//package com.bookxchange.service;
//
//import com.bookxchange.dto.Mapper;
//import com.bookxchange.dto.TransactionDto;
//import com.bookxchange.enums.TransactionType;
//import com.bookxchange.repositories.TransactionRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//class TransactionServiceTest {
//
//    @Mock
//    private  Mapper mapper;
//    @Mock
//    private  TransactionRepository transactionRepository;
//    @Mock
//    private  MemberService memberService;
//    @Mock
//    private  BookMarketService bookMarketService;
//    @Mock
//    private  BookService bookService;
//    @Mock
//    private  EmailService emailService;
//    @Mock
//    private  EmailTemplatesService emailTemplatesService;
//
//    @InjectMocks
//    private TransactionService transactionService;
//
//    public TransactionDto createTransactionDto(){
//         TransactionDto transactionDto= new TransactionDto();
//         transactionDto.setTransactionType(TransactionType.RENT);
//         return transactionDto;
//    }
//
//    @Test
//    void updateBookMarketStatusAndMemberPoints() {
//        when(bookMarketService.isBookMarketForRent(any())).thenReturn(true);
////        when(memberService.updatePointsToSupplierByID(any())).thenReturn()
////
//    }
//
//    @Test
//    void createTransaction() {
//    }
//
//    @Test
//    void sendEmail() {
//    }
//
//    @Test
//    void updateTransactionByUserTradeDecision() {
//    }
//
//    @Test
//    void getTransactionsByMemberUuIDAndType() {
//    }
//
//    @Test
//    void getTransactionByMemberUuid() {
//    }
//
//
//}