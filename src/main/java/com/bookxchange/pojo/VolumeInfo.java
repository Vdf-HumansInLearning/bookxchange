package com.bookxchange.pojo;

import lombok.Data;

import java.util.List;

@Data
public class VolumeInfo {

   private String title;
   private String subtitle;
   private String description;
   private List<String> authors;
}
