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
    @Column(name = "market_book_id")
    private String marketBookId;
    @Basic
    @Column(name = "member_id_from")
    private String memberIdFrom;
    @Basic
    @Column(name = "member_id_to")
    private String memberIdTo;
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
        this.marketBookId = marketBookId;
        this.memberIdFrom = memberIdFrom;
        this.memberIdTo = memberIdTo;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        if(transactionType.equalsIgnoreCase(TransactionType.RENT.toString())){
            this.expectedReturnDate = LocalDate.now().plusDays(30);
        }
    }


    public TransactionEntity() {

    }
}
