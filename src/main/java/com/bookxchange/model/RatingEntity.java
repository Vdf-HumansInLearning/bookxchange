package com.bookxchange.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "rating", schema = "bookOLX")
@Data
public class RatingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "rating_id")
    private int ratingId;
    @Basic
    @Column(name = "grade")
    private Integer grade;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "left_by")
    private String leftBy;
    @Basic
    @Column(name = "user_id")
    private String userId;
    @Basic
    @Column(name = "book_id")
    private String bookId;


}
