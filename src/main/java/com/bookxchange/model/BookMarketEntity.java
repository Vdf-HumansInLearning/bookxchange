package com.bookxchange.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "book_market", schema = "bookOLX")
@Data
public class BookMarketEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "book_market_id")
    private String bookMarketId;
    @Basic
    @Column(name = "user_id")
    private String userId;
    @Basic
    @Column(name = "book_id")
    private String bookId;
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


}
