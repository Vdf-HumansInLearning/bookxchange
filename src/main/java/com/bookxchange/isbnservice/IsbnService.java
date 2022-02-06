package com.bookxchange.isbnservice;


import com.bookxchange.isbnpojo.Isbn;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;

public class IsbnService {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    private String getInfoFromApi(String isbn) {
        String targetURL = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
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

    public HashMap<String, String> hitIsbnRequest(String isbn) {
        String infoFromApi = getInfoFromApi(isbn);
        Gson gson = new Gson();
        Isbn isbnDTO = gson.fromJson(infoFromApi, Isbn.class);
        HashMap<String, String> hashMap = new HashMap<>();
        if (isbnDTO.getTotalItems() != 0 && isbnDTO.getTotalItems() > 0) {
            String author = isbnDTO.getItems().get(0).getVolumeInfo().getTitle();
            hashMap.put(isbn, author);
        } else {
            hashMap.put(isbn, "Invalid isbn");
        }
        return hashMap;
    }
}
