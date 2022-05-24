package com.bookxchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "rating")
@Data
@AllArgsConstructor
public class RatingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "rating_id")
    private int ratingId;
    @Basic
    @Column(name = "grade")
    @NotNull(message = "grade is mandatory")
    private Integer grade;
    @Basic
    @Column(name = "description")
    @NotBlank(message = "description is mandatory")
    private String description;
    @Basic
    @Column(name = "left_by_uuid")
    @NotBlank(message = "the user who left the rating is mandatory")
    private String leftByUuid;
    @Basic
    @Column(name = "user_uuid")
    private String userIdUuid;
    @Basic
    @Column(name = "book_isbn")
    private String bookIsbn;


    public RatingEntity() {

    }

    public RatingEntity(Integer grade, String description, String leftByUuid, String userIdUuid, String bookIsbn) {
        this.grade = grade;
        this.description = description;
        this.leftByUuid = leftByUuid;
        this.userIdUuid = userIdUuid;
        this.bookIsbn = bookIsbn;
    }
}
