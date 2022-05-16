package com.bookxchange.model;

import com.bookxchange.enums.TransactionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TransactionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "market_book_uuid_supplier")
    private String marketBookIdSupplier;
    @Basic
    @Column(name = "market_book_uuid_client")
    private String marketBookIdClient;
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
    @Basic
    @Column(name = "transaction_status")
    private String transactionStatus;


    public TransactionEntity(String marketBookIdSupplier, String marketBookIdClient, String memberuuIdFrom, String memberuuIdTo, String transactionType) {
        this.marketBookIdSupplier = marketBookIdSupplier;
        this.marketBookIdClient = marketBookIdClient;
        this.memberuuIdFrom = memberuuIdFrom;
        this.memberuuIdTo = memberuuIdTo;
        this.transactionType = transactionType;
        this.transactionDate = LocalDate.now();
        if (transactionType.equalsIgnoreCase(TransactionType.RENT.toString())) {
            this.expectedReturnDate = LocalDate.now().plusDays(30);
        }
    }


    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
