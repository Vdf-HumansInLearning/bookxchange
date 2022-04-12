package RatingsController;

import com.bookxchange.BookExchangeApplication;
import com.bookxchange.model.RatingEntity;
import com.bookxchange.repositories.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK, classes={ BookExchangeApplication.class })
@AutoConfigureMockMvc
public class RatingsControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    TransactionRepository transactionRepository;

    //todo create transactions before creating ratings

    @Before
    public void createRentTransaction() throws Exception {

        mvc.perform(post("/transactions")
                        .content("{\n" +
                                "  \"marketBookId\": \"42a48524-20fd-4708-9311-55bf1a247eaf\",\n" +
                                "  \"supplier\": \"6eca21ce-861b-4dd7-975d-20a969e3183a\",\n" +
                                "  \"client\": \"13177e99-14b5-43c5-a446-e0dc751c3153\",\n" +
                                "  \"transactionType\": \"RENT\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    public void createMemberRating() throws Exception {

        mvc.perform(post("/ratings/createMemberRating")
                        .content("{\n" +
                                "   \"grade\": 4, \n" +
                                "   \"description\":\"un user bun\",\n" +
                                "   \"leftBy\":\"6eca21ce-861b-4dd7-975d-20a969e3183a\",\n" +
                                "   \"userId\":\"13177e99-14b5-43c5-a446-e0dc751c3153\",\n" +
                                "   \"bookId\": "+ null +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.grade").value("4"));
    }

    @Test
    public void createBookRating() throws Exception {

        mvc.perform(post("/ratings/createBookRating")
                        .content("{\n" +
                                "   \"grade\": 2, \n" +
                                "   \"description\":\"o carte buna\",\n" +
                                "   \"leftBy\":\"13177e99-14b5-43c5-a446-e0dc751c3153\",\n" +
                                "   \"userId\": \"13177e99-14b5-43c5-a446-e0dc751c3153\",\n" +
                                "   \"bookId\": \"0-7475-3269-9\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.grade").value("2"));
    }


    @Test
    public void createBookRatingInvalidBookId() throws Exception {

        mvc.perform(post("/ratings/createBookRating")
                        .content("{\n" +
                                "   \"grade\": 2, \n" +
                                "   \"description\":\"ceva\",\n" +
                                "   \"leftBy\":\"ae677979-ffec-4a90-a3e5-a5d1d31c0ee9\",\n" +
                                "   \"userId\": \"ae677979-ffec-4a90-a3e5-a5d1d31c0ee7\",\n" +
                                "   \"bookId\":" +null +
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
                                        null,"978-0-230-75700-4")))
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


   @After
    public void cleanUp() {
       transactionRepository.deleteAll(transactionRepository.findAllByTransactionDate(LocalDate.of(1999, 12, 12)));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
