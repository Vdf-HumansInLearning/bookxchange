package com.bookxchange.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Isbn {

    private String kind;
    private Integer totalItems;
    private List<Items> items;
}
