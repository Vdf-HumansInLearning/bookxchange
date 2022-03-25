package com.bookxchange.model;

import com.bookxchange.enums.TransactionType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transaction", schema = "bookOLX")
@Data
public class TransactionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "market_book_uuid")
    private String marketBookuuId;
    @Basic
    @Column(name = "member_uuid_from")
    private String memberuuIdFrom;
    @Basic
    @Column(name = "member_uuid_to")
    private String memberuuIdTo;
    @Basic
    @Column(name = "transaction_type")
    private String transactionType;
    @Basic
    @Column(name = "transaction_date")
    private LocalDate transactionDate;
    @Basic
    @Column(name = "expected_return_date")
    private LocalDate expectedReturnDate;

    public TransactionEntity(String marketBookId, String memberIdFrom, String memberIdTo, String transactionType, LocalDate transactionDate) {
        this.marketBookuuId = marketBookId;
        this.memberuuIdFrom = memberIdFrom;
        this.memberuuIdTo = memberIdTo;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        if(transactionType.equalsIgnoreCase(TransactionType.RENT.toString())){
            this.expectedReturnDate = LocalDate.now().plusDays(30);
        }
    }


    public TransactionEntity() {

    }
}
