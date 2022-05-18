package com.bookxchange.model;

import com.bookxchange.enums.BookStatus;
import com.bookxchange.exception.BookExceptions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "book_market")
@Getter
@Setter
@ToString
@Validated
public class BookMarketEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "book_market_id")
    private Integer bookMarketId;
    @Column(name = "book_market_uuid")
    @JsonIgnore
    private String bookMarketUuid;
    //    @Basic
    @Column(name = "user_uuid")
    @JsonIgnore
    private String userUuid;
    @Basic
    @Column(name = "book_isbn")
    @NotNull
    @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN can't be empty, it needs to 10 or 13 digits long (No dashes, or spaces are allowed)")
    private String bookIsbn;
    @Basic
    @Column(name = "book_state")
    private String bookState;
    @Basic
    @Column(name = "for_sell")
    private byte forSell;
    @Basic
    @Column(name = "sell_price")
    private Double sellPrice;
    @Basic
    @Column(name = "for_rent")
    private byte forRent;
    @Basic
    @Column(name = "rent_price")
    private Double rentPrice;
    @Basic
    @Column(name = "book_status")
    private String bookStatus;


    public BookMarketEntity() {
        this.bookMarketUuid = (UUID.randomUUID()).toString();
        this.bookStatus = BookStatus.AVAILABLE.toString();
    }

    ;

    public BookMarketEntity(String userUuid, String bookIsbn, String bookState, Byte forSell, Double sellPrice, Byte forRent, Double rentPrice) {
        this.userUuid = userUuid;
        this.bookIsbn = bookIsbn;
        this.bookState = bookState;
        this.forSell = forSell;
        this.sellPrice = sellPrice;
        this.forRent = forRent;
        this.rentPrice = rentPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BookMarketEntity that = (BookMarketEntity) o;
        return bookMarketId != null && Objects.equals(bookMarketId, that.bookMarketId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
