package com.bookxchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "rating", schema = "bookOLX")
@Data
@AllArgsConstructor
public class RatingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "rating_id")
    private int ratingId;
    @Basic
    @Column(name = "grade")
    @NotBlank(message = "grade is mandatory")
    private Integer grade;
    @Basic
    @Column(name = "description")
    @NotBlank(message = "description is mandatory")
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


    public RatingEntity() {

    }

    public RatingEntity(Integer grade, String description, String leftBy, String userId, String bookId) {
        this.grade = grade;
        this.description = description;
        this.leftBy = leftBy;
        this.userId = userId;
        this.bookId = bookId;
    }
}
