package com.bookxchange.service;

import com.bookxchange.customExceptions.InvalidRatingException;
import com.bookxchange.model.RatingEntity;
import com.bookxchange.model.TransactionEntity;
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

    @InjectMocks
    RatingService ratingService;

    public RatingEntity createRatingEntity(Integer grade, String description, String leftByUuid, String userIdUuid, String bookIsbn){
        RatingEntity ratingEntity = new RatingEntity( grade,  description,  leftByUuid,  userIdUuid,  bookIsbn);

        return ratingEntity;
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
    public void userIdUUidNullTest() {

        Exception exception = assertThrows(InvalidRatingException.class, () -> ratingService.ratingAMember(createRatingEntity(1,"ceva",null,null,"isbn")));
        assertEquals("User id can not be null when you rate a member",exception.getMessage());
    }

    @Test
    public void leftByUUidNullTest() {

        Exception exception = assertThrows(InvalidRatingException.class, () -> ratingService.ratingAMember(createRatingEntity(1,"ceva",null,null,"isbn")));
        assertEquals("User id can not be null when you rate a member",exception.getMessage());
    }

    @Test
    public void leftByUUidEqualsUserUuidTest() {

        Exception exception = assertThrows(InvalidRatingException.class, () -> ratingService.ratingAMember(createRatingEntity(1,"ceva","123","123","isbn")));
        assertEquals("Users can not let reviews to themselves",exception.getMessage());
    }


    @Test
    public void noTransactionAvailableTest() {

        when(transactionRepository.getTransactionByWhoSelleddAndWhoBuys(any(),any())).thenReturn(createTransactions());

        Exception exception = assertThrows(InvalidRatingException.class, () -> ratingService.ratingAMember(createRatingEntity(1,"ceva","123","1243","isbn")));
        assertEquals("These two users never interact",exception.getMessage());
    }
}
