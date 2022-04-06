package com.bookxchange.service;

import com.bookxchange.customExceptions.BooksExceptions;
import com.bookxchange.dto.Mapper;
import com.bookxchange.dto.TransactionDto;
import com.bookxchange.enums.BookStatus;
import com.bookxchange.enums.TransactionType;
import com.bookxchange.model.TransactionEntity;
import com.bookxchange.repositories.BookMarketRepository;
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
    public TransactionService(Mapper mapper, TransactionRepository transactionRepository, MemberService memberService, BookMarketService bookMarketService, BookMarketRepository bookMarketRepository, BookService bookService) {
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

    @Transactional
    public TransactionEntity createTransactionBuyingWithPoints(TransactionDto transactionDto) {
        TransactionEntity transactionEntity = mapper.toTransactionEntity(transactionDto);
            return transactionRepository.save(transactionEntity);
    }


    public List<TransactionEntity> getTransactionsByMemberUuIDAndType(String memberId, TransactionType type) {
        return transactionRepository.findAllByMemberUuIDAndTransactionType(memberId, type.toString());
    }

    public List<TransactionEntity> getTransactionByMemberUuid(String memberId) {
        return transactionRepository.findAllByMemberuuIdFrom(memberId);
    }

    public void updateBookMarketStatusAndMemberPoints(TransactionDto transactionDto) {
        if (transactionDto.getTransactionType().equals(TransactionType.RENT)) {
            memberService.updatePointsToSupplierByID(transactionDto.getSupplier());
            bookMarketService.updateBookMarketStatus(BookStatus.RENTED.toString(), transactionDto.getMarketBookId());
        } else {
            bookMarketService.updateBookMarketStatus(BookStatus.SOLD.toString(), transactionDto.getMarketBookId());
            memberService.updatePointsToSupplierByID(transactionDto.getSupplier());

            Double priceByMarketBookId = bookMarketService.getPriceByMarketBookId(transactionDto.getMarketBookId());
            memberService.updatePointsToClientById(priceByMarketBookId*10*-1, transactionDto.getClient());
            bookService.downgradeQuantityForTransaction(bookMarketService.getBookIsbn(transactionDto.getMarketBookId()));
        }
    }



    //TODO check points <0, or number of points if the transacation can be made
    @Transactional
    public void buyBookWithPoints(TransactionDto transactionDto) {

        if (isEligibleForBuy(transactionDto)) {
            updateBookMarketStatusAndMemberPoints(transactionDto);
            //TODO need to add if transaction is made with points or money
            createTransactionBuyingWithPoints(transactionDto);
        }
    }

    private boolean isEligibleForBuy(TransactionDto transactionDto) {
        return bookMarketService.getBookMarketStatus(transactionDto.getMarketBookId().toString()).equals(BookStatus.AVAILABLE.toString())
                && bookMarketService.isBookMarketForSell(transactionDto.getMarketBookId())
                && memberService.getPointsByMemberId(transactionDto.getClient()) / 10 >= bookMarketService.getPriceByMarketBookId(transactionDto.getMarketBookId());
    }

}
