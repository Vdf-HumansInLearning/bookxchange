package com.bookxchange.service;

import com.bookxchange.customExceptions.InvalidISBNException;
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
import java.util.HashMap;
import java.util.Properties;

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

  public HashMap<String, String> hitIsbnRequest(String isbn) {
    String infoFromApi = getInfoFromApi(isbn);
    Gson gson = new Gson();
    Isbn isbnDTO = gson.fromJson(infoFromApi, Isbn.class);
    HashMap<String, String> hashMap = new HashMap<>();
    if (isbnDTO.getTotalItems() > 0) {
      String title = isbnDTO.getItems().get(0).getVolumeInfo().getTitle();
      hashMap.put(isbn, title);
    } else {
      throw new InvalidISBNException(isbn + " is invalid");
    }
    return hashMap;
  }
}
