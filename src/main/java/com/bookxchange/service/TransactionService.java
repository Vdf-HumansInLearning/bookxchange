package com.bookxchange.service;

import com.bookxchange.customExceptions.BooksExceptions;
import com.bookxchange.customExceptions.NotEnoughPointsException;
import com.bookxchange.dto.Mapper;
import com.bookxchange.dto.TransactionDto;
import com.bookxchange.enums.BookStatus;
import com.bookxchange.enums.TransactionType;
import com.bookxchange.model.TransactionEntity;
import com.bookxchange.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private final Mapper mapper;
    private final TransactionRepository transactionRepository;
    private final MemberService memberService;
    private final BookMarketService bookMarketService;
    private final BookService bookService;

    @Autowired
    public TransactionService(Mapper mapper, TransactionRepository transactionRepository, MemberService memberService, BookMarketService bookMarketService, BookService bookService) {
        this.mapper = mapper;
        this.transactionRepository = transactionRepository;
        this.memberService = memberService;
        this.bookMarketService = bookMarketService;
        this.bookService = bookService;
    }

    @Transactional
    public TransactionEntity createTransaction(TransactionDto transactionDto) {
        TransactionEntity transactionEntity = mapper.toTransactionEntity(transactionDto);
        String bookMarketUuId = transactionEntity.getMarketBookuuId();
        String bookIsbn = bookMarketService.getBookIsbn(bookMarketUuId);
        String bookStatus = bookMarketService.getBookMarketStatus(bookMarketUuId);
            if (bookStatus.equals(BookStatus.AVAILABLE.toString())){
                updateBookMarketStatusAndMemberPoints(transactionDto);
                bookService.downgradeQuantityForTransaction(bookIsbn);
                return transactionRepository.save(transactionEntity);
            } throw new BooksExceptions("Book is not available at this time");
    }


    public List<TransactionEntity> getTransactionsByMemberUuIDAndType(String memberId, TransactionType type) {
        return transactionRepository.findAllByMemberUuIDAndTransactionType(memberId, type.toString());
    }

    public List<TransactionEntity> getTransactionByMemberUuid(String memberId) {
        return transactionRepository.findAllByMemberuuIdFrom(memberId);
    }
    @Transactional
    public void updateBookMarketStatusAndMemberPoints(TransactionDto transactionDto) {
        if (transactionDto.getTransactionType().equals(TransactionType.RENT)) {
            memberService.updatePointsToSupplierByID(transactionDto.getSupplier());
            bookMarketService.updateBookMarketStatus(BookStatus.RENTED.toString(), transactionDto.getMarketBookId());
        } else if (transactionDto.getTransactionType().equals(TransactionType.SELL)){
            bookMarketService.updateBookMarketStatus(BookStatus.SOLD.toString(), transactionDto.getMarketBookId());
            memberService.updatePointsToSupplierByID(transactionDto.getSupplier());
        } else if(transactionDto.getTransactionType().equals(TransactionType.POINTSELL)&&isEligibleForBuy(transactionDto)){
            Double priceByMarketBookId = bookMarketService.getPriceByMarketBookId(transactionDto.getMarketBookId());
            bookMarketService.updateBookMarketStatus(BookStatus.SOLD.toString(), transactionDto.getMarketBookId());
            memberService.updatePointsToSupplierByID(transactionDto.getSupplier());
            memberService.updatePointsToClientById(priceByMarketBookId*10*-1, transactionDto.getClient());
        } else throw new RuntimeException("Transaction Invalid");
    }

    private boolean isEligibleForBuy(TransactionDto transactionDto) {
        if( (memberService.getPointsByMemberId(transactionDto.getClient()) / 10) >= bookMarketService.getPriceByMarketBookId(transactionDto.getMarketBookId())){
            return true;
        }throw new NotEnoughPointsException("Member is not eligible for buying");
    }



}
