package com.bookxchange.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "book_market", schema = "bookOLX")
@Getter
@Setter
@ToString
public class BookMarketEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "book_market_id")
    private Integer bookMarketId;
    @Column(name = "book_market_uuid")
    private String bookMarketUuid;
    @Basic
    @Column(name = "user_uuid")
    private String userUuid;
    @Basic
    @Column(name = "book_isbn")
    private String bookIsbn;
    @Basic
    @Column(name = "book_state")
    private String bookState;
    @Basic
    @Column(name = "for_sell")
    private Byte forSell;
    @Basic
    @Column(name = "sell_price")
    private Double sellPrice;
    @Basic
    @Column(name = "for_rent")
    private Byte forRent;
    @Basic
    @Column(name = "rent_price")
    private Double rentPrice;
    @Basic
    @Column(name = "book_status")
    private String bookStatus;



    public BookMarketEntity(){
        this.bookMarketUuid = (UUID.randomUUID()).toString();
    };

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
