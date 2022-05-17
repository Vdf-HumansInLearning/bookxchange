package com.bookxchange.service.it;

import com.bookxchange.BookExchangeApplication;
import com.bookxchange.model.RatingEntity;
import com.bookxchange.model.TransactionEntity;
import com.bookxchange.repository.TransactionRepository;
import com.bookxchange.service.BookMarketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {BookExchangeApplication.class})
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
public class RatingServiceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BookMarketService bookMarketService;

    @Before
    public void createRentTransaction() throws Exception {

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionType("RENT");
        transactionEntity.setMarketBookIdSupplier("42a48524-20fd-4708-9311-55bf1a247eaf");
        transactionEntity.setMemberuuIdFrom("6eca21ce-861b-4dd7-975d-20a969e3183a");
        transactionEntity.setMemberuuIdTo("13177e99-14b5-43c5-a446-e0dc751c3153");
        transactionRepository.save(transactionEntity);


    }


    @Test
    public void createMemberRating() throws Exception {

        mvc.perform(post("/ratings/createMemberRating")
                        .content("{\n" +
                                "   \"grade\": 4, \n" +
                                "   \"description\":\"un user bun\",\n" +
                                "   \"leftBy\":\"6eca21ce-861b-4dd7-975d-20a969e3183a\",\n" +
                                "   \"userId\":\"13177e99-14b5-43c5-a446-e0dc751c3153\",\n" +
                                "   \"bookId\": " + null +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.grade").value("4"));
    }


    @Test
    public void createBookRatingInvalidBookId() throws Exception {

        mvc.perform(post("/ratings/createBookRating")
                        .content("{\n" +
                                "   \"grade\": 2, \n" +
                                "   \"description\":\"ceva\",\n" +
                                "   \"leftBy\":\"ae677979-ffec-4a90-a3e5-a5d1d31c0ee9\",\n" +
                                "   \"userId\": \"ae677979-ffec-4a90-a3e5-a5d1d31c0ee7\",\n" +
                                "   \"bookId\":" + null +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Book id can not be null when you rate a book"));
    }

    @Test
    public void createBookRatingUsersNeverInteract() throws Exception {

        mvc.perform(post("/ratings/createBookRating")
                        .content("{\n" +
                                "   \"grade\": 2, \n" +
                                "   \"description\":\"ceva\",\n" +
                                "   \"leftBy\":\"ae677979-ffec-4a90-a3e5-a5d1d31c0ee9\",\n" +
                                "   \"userId\": \"ae677979-ffec-4a90-a3e5-a5d1d31c0ee7\",\n" +
                                "   \"bookId\": \"978-0-230-75700-3\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("This user ae677979-ffec-4a90-a3e5-a5d1d31c0ee9 doesn't interact with this book"));
    }

    @Test
    public void createMemberRatingInvalidUserId() throws Exception {

        mvc.perform(post("/ratings/createMemberRating")
                        .content(
                                asJsonString(new RatingEntity(4, "user bun",
                                        " ae677979-ffec-4a90-a3e5-a5d1d31c0ee9",
                                        null, "978-0-230-75700-4")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User id can not be null when you rate a member"));
    }

    @Test
    public void createMemberRatingUsersNeverInteract() throws Exception {

        mvc.perform(post("/ratings/createMemberRating")
                        .content("{\n" +
                                "   \"grade\": 4, \n" +
                                "   \"description\":\"ceva\",\n" +
                                "   \"leftBy\":\"ae677979-ffec-4a90-a3e5-a5d1d31c0ee9\",\n" +
                                "   \"userId\":\"6eca21ce-861b-4dd7-975d-20a969e3183a\",\n" +
                                "   \"bookId\": null\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("These two users never interact"));
    }

    @Test
    public void createMemberRatingUserCanNotLetRatingsToThemselves() throws Exception {

        mvc.perform(post("/ratings/createMemberRating")
                        .content("{\n" +
                                "   \"grade\": 4, \n" +
                                "   \"description\":\"ceva\",\n" +
                                "   \"leftBy\":\"ae677979-ffec-4a90-a3e5-a5d1d31c0ee9\",\n" +
                                "   \"userId\":\"ae677979-ffec-4a90-a3e5-a5d1d31c0ee9\",\n" +
                                "   \"bookId\": null\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Users can not let reviews to themselves"));
    }

    @Test
    public void contextLoads() {
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
