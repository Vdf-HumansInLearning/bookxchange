package com.bookxchange.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Isbn {
    //TODO make those private
    String kind;
    Integer totalItems;
    List<Items> items;
}
