package com.bookxchange.service.unitTest;

import com.bookxchange.dto.TransactionDTO;
import com.bookxchange.enums.TransactionType;
import com.bookxchange.mapper.Mapper;
import com.bookxchange.repository.TransactionRepository;
import com.bookxchange.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class TransactionServiceTest {

    @Mock
    private  Mapper mapper;
    @Mock
    private  TransactionRepository transactionRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private BookMarketService bookMarketService;
    @Mock
    private BookService bookService;
    @Mock
    private EmailService emailService;
    @Mock
    private EmailTemplatesService emailTemplatesService;

    @InjectMocks
    private TransactionService transactionService;


    public TransactionDTO createTransactionDto(){
         TransactionDTO transactionDto= new TransactionDTO();
         transactionDto.setTransactionType(TransactionType.RENT);
         return transactionDto;
    }

//    @Test
//    @Disabled
//    void updateBookMarketStatusAndMemberPoints() {
//        when(bookMarketService.isBookMarketForRent(any())).thenReturn(true);
////        when(memberService.updatePointsToSupplierByID(any())).thenReturn()
//// s verify ca book
//    }

    @Test
    void createTransaction() {
    }

    @Test
    void sendEmail() {
    }

    @Test
    void updateTransactionByUserTradeDecision() {
    }

    @Test
    void getTransactionsByMemberUuIDAndType() {
    }

    @Test
    void getTransactionByMemberUuid() {
    }


}