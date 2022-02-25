package com.bookxchange.service;


import com.bookxchange.customExceptions.InvalidISBNException;
import com.bookxchange.model.Author;
import com.bookxchange.model.Book;
import com.bookxchange.pojo.Isbn;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import utils.PropertyLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.UUID;

public class IsbnService {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    private String getInfoFromApi(String isbn) {
        String targetURL;
        Properties properties = PropertyLoader.loadProperties();
        targetURL = properties.getProperty("ISBN_API_URL") + isbn;

        String result = "";
        HttpGet request = new HttpGet(targetURL);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            System.out.println(response.getStatusLine().toString());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

//    public HashMap<String, String> hitIsbnRequest(String isbn) {
//        String infoFromApi = getInfoFromApi(isbn);
//        Gson gson = new Gson();
//        Isbn isbnDTO = gson.fromJson(infoFromApi, Isbn.class);
//        HashMap<String, String> hashMap = new HashMap<>();
//        if (isbnDTO.getTotalItems() > 0) {
//            String title = isbnDTO.getItems().get(0).getVolumeInfo().getTitle();
//            hashMap.put(isbn, title);
//        } else {
//            throw new InvalidISBNException(isbn + " is invalid");
//        }
//        return hashMap;
//    }

    public Book hitIsbnBookRequest(String isbn) {
        String infoFromApi = getInfoFromApi(isbn);
        System.out.println(infoFromApi);
        Gson gson = new Gson();
        Isbn isbnDTO = gson.fromJson(infoFromApi, Isbn.class);
        Book bookToReturn = new Book();
        if (isbnDTO.getTotalItems() > 0) {


            bookToReturn.setIsbn(isbn);
            bookToReturn.setTitle(isbnDTO.getItems().get(0).getVolumeInfo().getTitle());

            List<String> author = isbnDTO.getItems().get(0).getVolumeInfo().getAuthors();
            List<Author> authorsToAdd= new ArrayList<>();
            for(int i=0; i<author.size(); i++) {
                Author selectedAuthor=new Author();
                Pattern patternAuthorName= Pattern.compile("^([a-zA-Z]{2,}\\s[a-zA-Z]{1,}'?-?[a-zA-Z]{2,}\\s?([a-zA-Z]{1,})?)");
                Matcher matcher = patternAuthorName.matcher(author.get(i));
                if (matcher.find()) {
                    selectedAuthor.setName(matcher.group(2));
                    selectedAuthor.setSurname(matcher.group(1));
                    selectedAuthor.setId(UUID.randomUUID());
                }
                authorsToAdd.add(selectedAuthor);
            }

            bookToReturn.setAuthors(authorsToAdd);

            bookToReturn.setDescription(isbnDTO.getItems().get(0).getVolumeInfo().getDescription());
        } else {
            throw new InvalidISBNException(isbn + " is invalid");
        }
        return bookToReturn;
    }

}
