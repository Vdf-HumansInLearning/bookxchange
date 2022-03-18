package com.bookxchange.model;

import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
public class Author {

    private UUID id;
    private String name;
    private String surname;

}
