package com.bookxchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {


    private Integer grade;
    private String description;
    private String leftBy;
    private String userId;
    private String bookId;


}
