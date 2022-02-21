package com.bookxchange.pojo;

import lombok.Data;

import java.util.List;

@Data
public class VolumeInfo {
    //TODO make those private
    String title;
    String subtitle;
    List<String> authors;
}
