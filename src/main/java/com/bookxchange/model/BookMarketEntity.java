package com.bookxchange.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "BookMarket")
@Data
public class BookMarketEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "userID")
    private String userId;
    @Basic
    @Column(name = "bookID")
    private String bookId;
    @Basic
    @Column(name = "bookState")
    private String bookState;
    @Basic
    @Column(name = "forSell")
    private Byte forSell;
    @Basic
    @Column(name = "sellPrice")
    private Double sellPrice;
    @Basic
    @Column(name = "forRent")
    private Byte forRent;
    @Basic
    @Column(name = "rentPrice")
    private Double rentPrice;
    @Basic
    @Column(name = "bookStatus")
    private String bookStatus;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookMarketEntity that = (BookMarketEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (bookId != null ? !bookId.equals(that.bookId) : that.bookId != null) return false;
        if (bookState != null ? !bookState.equals(that.bookState) : that.bookState != null) return false;
        if (forSell != null ? !forSell.equals(that.forSell) : that.forSell != null) return false;
        if (sellPrice != null ? !sellPrice.equals(that.sellPrice) : that.sellPrice != null) return false;
        if (forRent != null ? !forRent.equals(that.forRent) : that.forRent != null) return false;
        if (rentPrice != null ? !rentPrice.equals(that.rentPrice) : that.rentPrice != null) return false;
        if (bookStatus != null ? !bookStatus.equals(that.bookStatus) : that.bookStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (bookId != null ? bookId.hashCode() : 0);
        result = 31 * result + (bookState != null ? bookState.hashCode() : 0);
        result = 31 * result + (forSell != null ? forSell.hashCode() : 0);
        result = 31 * result + (sellPrice != null ? sellPrice.hashCode() : 0);
        result = 31 * result + (forRent != null ? forRent.hashCode() : 0);
        result = 31 * result + (rentPrice != null ? rentPrice.hashCode() : 0);
        result = 31 * result + (bookStatus != null ? bookStatus.hashCode() : 0);
        return result;
    }
}
