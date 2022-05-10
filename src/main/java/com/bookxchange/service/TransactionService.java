package com.bookxchange.service;

import com.bookxchange.customExceptions.BooksExceptions;
import com.bookxchange.customExceptions.TransactionException;
import com.bookxchange.dto.Mapper;
import com.bookxchange.dto.TransactionDto;
import com.bookxchange.enums.TransactionStatus;
import com.bookxchange.enums.BookStatus;
import com.bookxchange.enums.TransactionType;
import com.bookxchange.model.*;
import com.bookxchange.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final EmailService emailService;
    private final EmailTemplatesService emailTemplatesService;
    private final String approveResponse = "approve";


    @Value("${server.port}")
    private String applicationPort;
    @Value("${application.url}")
    private String applicationTradeUrl;


    @Autowired
    public TransactionService(Mapper mapper, TransactionRepository transactionRepository, MemberService memberService, BookMarketService bookMarketService, BookService bookService, EmailService emailService, EmailTemplatesService emailTemplatesService) {
        this.mapper = mapper;
        this.transactionRepository = transactionRepository;
        this.memberService = memberService;
        this.bookMarketService = bookMarketService;
        this.bookService = bookService;
        this.emailService = emailService;
        this.emailTemplatesService = emailTemplatesService;
    }

    @Transactional
    public TransactionEntity createTransaction(TransactionDto transactionDto, String token) {
        transactionDto.setSupplier(ApplicationUtils.getUserFromToken(token));
        TransactionEntity transactionEntity = mapper.toTransactionEntity(transactionDto);
        if (transactionDto.getTransactionType() != TransactionType.TRADE) {
            transactionEntity.setTransactionStatus(TransactionStatus.SUCCESS.toString());
            String bookMarketUuId = transactionEntity.getMarketBookIdSupplier();
            String bookIsbn = bookMarketService.getBookIsbn(bookMarketUuId);
            String bookStatus = bookMarketService.getBookMarketStatus(bookMarketUuId);
            if (bookStatus.equals(BookStatus.AVAILABLE.toString())) {
                updateBookMarketStatusAndMemberPointsForTransactionType(transactionDto);
                bookService.downgradeQuantityForTransaction(bookIsbn);
                return transactionRepository.save(transactionEntity);
            }
            throw new BooksExceptions("Book is not available at this time");
        } else {
            transactionEntity.setTransactionStatus(TransactionStatus.PENDING.toString());
            transactionRepository.save(transactionEntity);
            sendEmail(transactionDto);
            return transactionEntity;
        }
    }

    public void sendEmail(TransactionDto transactionDto) {

        EmailTemplatesEntity emailTemplate = emailTemplatesService.getById(2);
        MembersEntity client = memberService.findByUuid(transactionDto.getClient());
        MembersEntity supplier = memberService.findByUuid(transactionDto.getSupplier());
        String clientBookIsbn = bookMarketService.getBookIsbn(transactionDto.getMarketBookIdClient());
        String supplierBookIsbn = bookMarketService.getBookIsbn(transactionDto.getMarketBookIdSupplier());
        BooksEntity clientBook = bookService.getBookByIsbn(clientBookIsbn);
        BooksEntity supplierBook = bookService.getBookByIsbn(supplierBookIsbn);

        List<TransactionEntity> transactionEntities = transactionRepository.findTransactionEntityByMarketBookIdClientAndMarketBookIdSupplierAndTransactionTypeAndTransactionStatus(transactionDto.getMarketBookIdClient(), transactionDto.getMarketBookIdSupplier(), TransactionType.TRADE.toString(), TransactionStatus.PENDING.toString());
        if (transactionEntities.size() > 1) {
            throw new TransactionException("Mai aveti deja o tranzactie in curs intre aceste doua carti");
        }
        TransactionEntity transaction = transactionEntities.get(0);
        String approveUrl = String.format(applicationTradeUrl, applicationPort, approveResponse, transaction.getId());
        String denyUrl = String.format(applicationTradeUrl, applicationPort, "deny", transaction.getId());
        String body = String.format(emailTemplate.getContentBody(), client.getUsername(), clientBook.getTitle(), supplier.getUsername(), supplierBook.getTitle(), approveUrl, denyUrl);
        emailService.sendMail(client.getEmailAddress(), emailTemplate.getSubject(), body);

    }

    public void updateTransactionByUserTradeDecision(String userTradeAnswer, String transactionId) {

        long transIdLong = Long.parseLong(transactionId);
        TransactionEntity transaction = transactionRepository.findById(transIdLong).get();
        if (userTradeAnswer.equals(approveResponse)) {
            transaction.setTransactionStatus(TransactionStatus.SUCCESS.toString());
        } else {
            transaction.setTransactionStatus(TransactionStatus.FAILURE.toString());
        }
        transactionRepository.save(transaction);

    }


    public List<TransactionEntity> getTransactionsByMemberUuIDAndType(String memberId, TransactionType type) {
        return transactionRepository.findAllByMemberUuIDAndTransactionType(memberId, type.toString());
    }

    public List<TransactionEntity> getTransactionByMemberUuid(String memberId) {
        return transactionRepository.findAllByMemberuuIdFrom(memberId);
    }


    @Transactional
    public void updateBookMarketStatusAndMemberPointsForTransactionType(TransactionDto transactionDto) {
//        TODO: mai e nevoie sa verific flagul de isForSell isForRent???
        if (transactionDto.getTransactionType().equals(TransactionType.RENT) && bookMarketService.isBookMarketForRent(transactionDto.getMarketBookIdSupplier())) {
            memberService.updatePointsToSupplierByID(transactionDto.getSupplier());
            bookMarketService.updateBookMarketStatus(BookStatus.RENTED.toString(), transactionDto.getMarketBookIdSupplier());
        } else if (transactionDto.getTransactionType().equals(TransactionType.SELL) && bookMarketService.isBookMarketForSell(transactionDto.getMarketBookIdSupplier())) {
            bookMarketService.updateBookMarketStatus(BookStatus.SOLD.toString(), transactionDto.getMarketBookIdSupplier());
            memberService.updatePointsToSupplierByID(transactionDto.getSupplier());
        } else if (transactionDto.getTransactionType().equals(TransactionType.POINTSELL) && isEligibleForBuy(transactionDto)) {
            Double priceByMarketBookId = bookMarketService.getPriceByMarketBookId(transactionDto.getMarketBookIdSupplier());
            bookMarketService.updateBookMarketStatus(BookStatus.SOLD.toString(), transactionDto.getMarketBookIdSupplier());
            memberService.updatePointsToSupplierByID(transactionDto.getSupplier());
            memberService.updatePointsToClientById(bookMarketService.moneyToPoints(priceByMarketBookId), transactionDto.getClient());
        } else throw new TransactionException("Invalid Transaction");
    }

    private boolean isEligibleForBuy(TransactionDto transactionDto) {
        if ((memberService.getPointsByMemberId(transactionDto.getClient()) / 10) >= bookMarketService.getPriceByMarketBookId(transactionDto.getMarketBookIdSupplier())) {
            return true;
        }
        throw new TransactionException("Member is not eligible for buying");
    }


}
