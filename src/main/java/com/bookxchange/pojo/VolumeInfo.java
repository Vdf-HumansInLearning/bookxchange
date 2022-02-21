package com.bookxchange.pojo;

import lombok.Data;

import java.util.List;

@Data
public class VolumeInfo {
  String title;
  String subtitle;
  List<String> authors;
}
