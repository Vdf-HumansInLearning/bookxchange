package com.bookxchange.service.unitTest;

import com.bookxchange.customExceptions.InvalidRatingException;
import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.model.RatingEntity;
import com.bookxchange.model.TransactionEntity;
import com.bookxchange.repositories.BookMarketRepository;
import com.bookxchange.repositories.TransactionRepository;
import com.bookxchange.service.RatingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
public class RatingServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    BookMarketRepository bookMarketRepository;


    @InjectMocks
    RatingService ratingService;

    public RatingEntity createRatingEntity(Integer grade, String description, String leftByUuid, String userIdUuid, String bookIsbn){
        RatingEntity ratingEntity = new RatingEntity( grade,  description,  leftByUuid,  userIdUuid,  bookIsbn);

        return ratingEntity;
    }


     public BookMarketEntity createBookMarketEntity(String userUuid, String bookIsbn, String bookState, Byte forSell, Double sellPrice, Byte forRent, Double rentPrice){

        BookMarketEntity bookMarketEntity = new BookMarketEntity( userUuid,  bookIsbn,  bookState,  forSell,  sellPrice,  forRent,  rentPrice);
        return bookMarketEntity;
     }

    public List<TransactionEntity> createTransactions(){

        List<TransactionEntity> transactionEntitiesList = new ArrayList<>();
        return  transactionEntitiesList;
    }

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void ratingAMemberUserIdUUidNullTest() {

        Exception exception = assertThrows(InvalidRatingException.class, () -> ratingService.ratingAMember(createRatingEntity(1,"ceva",null,null,"isbn")));
        assertEquals("User id can not be null when you rate a member",exception.getMessage());
    }

    @Test
    public void ratingAMemberLeftByUUidNullTest() {

        Exception exception = assertThrows(InvalidRatingException.class, () -> ratingService.ratingAMember(createRatingEntity(1,"ceva",null,null,"isbn")));
        assertEquals("User id can not be null when you rate a member",exception.getMessage());
    }

    @Test
    public void ratingAMemberLeftByUUidEqualsUserUuidTest() {

        Exception exception = assertThrows(InvalidRatingException.class, () -> ratingService.ratingAMember(createRatingEntity(1,"ceva","123","123","isbn")));
        assertEquals("Users can not let reviews to themselves",exception.getMessage());
    }


    @Test
    public void ratingAMemberNoTransactionAvailableTest() {

        when(transactionRepository.getTransactionByWhoSelleddAndWhoBuys(any(),any())).thenReturn(createTransactions());

        Exception exception = assertThrows(InvalidRatingException.class, () -> ratingService.ratingAMember(createRatingEntity(1,"ceva","123","1243","isbn")));
        assertEquals("These two users never interact",exception.getMessage());
    }


    @Test
    public void ratingABookInvalidIsbnOrNull() {

        Exception exception = assertThrows(InvalidRatingException.class, () -> ratingService.ratingABook(createRatingEntity(1,"ceva","123","1243",null)));
        assertEquals("Book id can not be null when you rate a book",exception.getMessage());
    }

    @Test
    public void ratingABookBookMarketNull() {

        when(bookMarketRepository.getBookMarketEntityByBookId(any())).thenReturn(null);

        Exception exception = assertThrows(InvalidRatingException.class, () -> ratingService.ratingABook(createRatingEntity(1,"ceva","123","1243","123")));
        assertEquals("This user 123 doesn't interact with this book",exception.getMessage());
    }

    @Test
    public void ratingBookNoTransactionAvailableTest() {

        when(transactionRepository.getTransactionByWhoSelleddAndWhoBuys(any(),any())).thenReturn(createTransactions());
        when(bookMarketRepository.getBookMarketEntityByBookId(any())).thenReturn(createBookMarketEntity("uuit","ceva","noua", (byte) 1,3d, (byte) 1,2d));

        Exception exception = assertThrows(InvalidRatingException.class, () -> ratingService.ratingABook(createRatingEntity(1,"ceva","123","1243","123")));
        assertEquals("This user 123 doesn't interact with this book",exception.getMessage());
    }


}
