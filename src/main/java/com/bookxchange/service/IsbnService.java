package com.bookxchange.service;


import com.bookxchange.customExceptions.InvalidISBNException;
import com.bookxchange.model.AuthorsEntity;
import com.bookxchange.model.BooksEntity;
import com.bookxchange.pojo.Isbn;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import utils.PropertyLoader;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IsbnService {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    private String getInfoFromApi(String isbn) {
        String targetURL;
        Properties properties = PropertyLoader.loadProperties();
        targetURL = properties.getProperty("ISBN_API_URL") + isbn;

        String result = "";
        HttpGet request = new HttpGet(targetURL);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }



    public BooksEntity hitIsbnBookRequest(String isbn) {
        String infoFromApi = getInfoFromApi(isbn);
        Gson gson = new Gson();
        Isbn isbnDTO = gson.fromJson(infoFromApi, Isbn.class);
        BooksEntity bookToReturn = new BooksEntity();
        if (isbnDTO.getTotalItems() > 0) {


            bookToReturn.setIsbn(isbn);
            bookToReturn.setTitle(isbnDTO.getItems().get(0).getVolumeInfo().getTitle());

            List<String> author = isbnDTO.getItems().get(0).getVolumeInfo().getAuthors();
            Set<AuthorsEntity> authorsToAdd= new HashSet<>();
            for(int i=0; i<author.size(); i++) {
                AuthorsEntity selectedAuthor=new AuthorsEntity();
                Pattern patternAuthorName= Pattern.compile("^(.*)\\s([a-zA-Z]{1,})$");
                Matcher matcher = patternAuthorName.matcher(author.get(i));
                if (matcher.find()) {
                    selectedAuthor.setName(matcher.group(2));
                    selectedAuthor.setSurname(matcher.group(1));
                    selectedAuthor.setAuthorsUuid(UUID.randomUUID().toString());
                }
                authorsToAdd.add(selectedAuthor);
            }

            bookToReturn.setAuthors(authorsToAdd);

            bookToReturn.setDescription(isbnDTO.getItems().get(0).getVolumeInfo().getDescription());
            bookToReturn.setQuantity(0);
        } else {
            throw new InvalidISBNException(isbn + " is invalid");
        }
        return bookToReturn;
    }

}
