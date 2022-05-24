package com.bookxchange.service;

import com.bookxchange.dto.TransactionDTO;
import com.bookxchange.enums.BookStatus;
import com.bookxchange.enums.TransactionStatus;
import com.bookxchange.enums.TransactionType;
import com.bookxchange.exception.BookExceptions;
import com.bookxchange.exception.InvalidTransactionException;
import com.bookxchange.mapper.Mapper;
import com.bookxchange.model.*;
import com.bookxchange.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
//
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
    public TransactionEntity createTransaction(TransactionDTO transactionDto, String token) {
        transactionDto.setClientId(ApplicationUtils.getUserFromToken(token));
        TransactionEntity transactionEntity = mapper.toTransactionEntity(transactionDto);
        BookMarketEntity bookMarketEntity = bookMarketService.getBookMarketFromOptional(transactionDto.getMarketBookUuidSupplier());
        if (transactionDto.getTransactionType() != TransactionType.TRADE) {
            transactionEntity.setTransactionStatus(TransactionStatus.SUCCESS.toString());
            String bookIsbn = bookMarketEntity.getBookIsbn();
            String bookStatus = bookMarketEntity.getBookStatus();
            if (bookStatus.equals(BookStatus.AVAILABLE.toString())) {
                updateBookMarketStatusAndMemberPoints(transactionDto, bookMarketEntity) ;
                bookService.downgradeQuantityForTransaction(bookIsbn);
            } else {
                throw new BookExceptions("Book is not available at this time");
            }
        } else {
            transactionEntity.setTransactionStatus(TransactionStatus.PENDING.toString());

        }
        try{
            sendEmail(transactionDto);
        }catch(Exception e){
            throw  new RuntimeException(e.getMessage());
        }

        transactionRepository.save(transactionEntity);
        return transactionEntity;
    }

    public void sendEmail(TransactionDTO transactionDto) {

        ExecutorService executor = Executors.newFixedThreadPool(10);

        Runnable runnableTask = () -> {
            try {
                MemberEntity client;
                EmailTemplatesEntity emailTemplate;
                String body;
                if (transactionDto.getTransactionType() == TransactionType.TRADE) {

                    emailTemplate = emailTemplatesService.getByTemplateName("TRADE");
//                            getById(3);
                    client = memberService.findByUuid(transactionDto.getClientId());
                    MemberEntity supplier = memberService.findByUuid(transactionDto.getSupplierId());
                    String clientBookIsbn = bookMarketService.getBookIsbn(transactionDto.getMarketBookUuidClient());
                    String supplierBookIsbn = bookMarketService.getBookIsbn(transactionDto.getMarketBookUuidSupplier());
                    BookEntity clientBook = bookService.getBookByIsbn(clientBookIsbn);
                    BookEntity supplierBook = bookService.getBookByIsbn(supplierBookIsbn);

                    List<TransactionEntity> transactionEntities = transactionRepository.findTransactionEntityByMarketBookIdClientAndMarketBookIdSupplierAndTransactionTypeAndTransactionStatus(transactionDto.getMarketBookUuidClient(), transactionDto.getMarketBookUuidSupplier(), TransactionType.TRADE.toString(), TransactionStatus.PENDING.toString());
                    if (transactionEntities.size() > 1) {
                        throw new InvalidTransactionException("You already have an ongoing transaction between these two books");
                    }
                    TransactionEntity transaction = transactionEntities.get(0);
                    String approveUrl = String.format(applicationTradeUrl, applicationPort, approveResponse, transaction.getId());
                    String denyUrl = String.format(applicationTradeUrl, applicationPort, "deny", transaction.getId());
                    body = String.format(emailTemplate.getContentBody(), client.getUsername(), clientBook.getTitle(), supplier.getUsername(), supplierBook.getTitle(), approveUrl, denyUrl);
                } else {
                    emailTemplate = emailTemplatesService.getByTemplateName("TRANSACTION_SUCCESS");
//                            getById(4);
                    client = memberService.findByUuid(transactionDto.getClientId());
                    body =client.getUsername();
                }
                emailService.sendMail(client.getEmailAddress(), emailTemplate.getSubject(), body);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        executor.execute(runnableTask);


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
        return transactionRepository.findAllByMemberuuIdTo(memberId);
    }

    @Transactional
    public void updateBookMarketStatusAndMemberPoints(TransactionDTO transactionDto, BookMarketEntity bookMarketEntity) {
        if (transactionDto.getTransactionType().equals(TransactionType.RENT) && bookMarketEntity.getForRent()==1) {
            memberService.updatePointsToSupplierByID(transactionDto.getSupplierId());
            bookMarketService.updateBookMarketStatus(BookStatus.RENTED.toString(), transactionDto.getMarketBookUuidSupplier());
         
        } else if (transactionDto.getTransactionType().equals(TransactionType.SELL)&& bookMarketEntity.getForSell()==1) {
            bookMarketService.updateBookMarketStatus(BookStatus.SOLD.toString(), transactionDto.getMarketBookUuidSupplier());
            memberService.updatePointsToSupplierByID(transactionDto.getSupplierId());
          
        } else if (transactionDto.getTransactionType().equals(TransactionType.POINTSELL) && isEligibleForBuy(transactionDto)) {
            Double priceByMarketBookId = bookMarketService.getPriceByMarketBookId(transactionDto.getMarketBookUuidSupplier());
            bookMarketService.updateBookMarketStatus(BookStatus.SOLD.toString(), transactionDto.getMarketBookUuidSupplier());
            memberService.updatePointsToSupplierByID(transactionDto.getSupplierId());
            memberService.updatePointsToClientById(bookMarketService.moneyToPoints(priceByMarketBookId), transactionDto.getClientId());
        } else throw new InvalidTransactionException("Invalid Transaction conditions");
    }

    private boolean isEligibleForBuy(TransactionDTO transactionDto) {
        return (memberService.getPointsByMemberId(transactionDto.getClientId()) / 10) >= bookMarketService.getPriceByMarketBookId(transactionDto.getMarketBookUuidSupplier());
    }


}
