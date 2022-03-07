package RatingsController;

import com.bookxchange.BookExchangeApplication;
import com.bookxchange.model.RatingEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK, classes={ BookExchangeApplication.class })
@AutoConfigureMockMvc
public class RatingsControllerTests {

    @Autowired
    private MockMvc mvc;

    //todo create transactions before creating ratings

    @Test
    public void createMemberRating() throws Exception {

        mvc.perform(post("/ratings/createMemberRating")
                        .content("{\n" +
                                "   \"grade\": 4, \n" +
                                "   \"description\":\"un user bun\",\n" +
                                "   \"leftBy\":\"ae677979-ffec-4a90-a3e5-a5d1d31c0ee9\",\n" +
                                "   \"userId\":\"13177e99-14b5-43c5-a446-e0dc751c3153\",\n" +
                                "   \"bookId\": \"978-0-230-75700-4\"\n" +
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
                                "   \"userId\": null,\n" +
                                "   \"bookId\": \"978-0-230-75700-4\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
            //    .andExpect(jsonPath("$.grade").value("2"));
    }


    @Test
    public void createBookRatingInvalidBookId() throws Exception {

        mvc.perform(post("/ratings/createBookRating")
                        .content("{\n" +
                                "   \"grade\": 2, \n" +
                                "   \"description\":\"ceva\",\n" +
                                "   \"leftBy\":\"ae677979-ffec-4a90-a3e5-a5d1d31c0ee9\",\n" +
                                "   \"userId\": \"ae677979-ffec-4a90-a3e5-a5d1d31c0ee9\",\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
              //  .andExpect(jsonPath("$.message").value("Book id can not be null when you rate a book"));
    }

    @Test
    public void createBookRatingUsersNeverInteract() throws Exception {

        mvc.perform(post("/ratings/createBookRating")
                        .content("{\n" +
                                "   \"grade\": 2, \n" +
                                "   \"description\":\"ceva\",\n" +
                                "   \"leftBy\":\"ae677979-ffec-4a90-a3e5-a5d1d31c0ee9\",\n" +
                                "   \"userId\": \"ae677979-ffec-4a90-a3e5-a5d1d31c0ee7\",\n" +
                                "   \"bookId\": \"978-0-230-75700-4\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
               // .andExpect(jsonPath("$.message").value("This user ae677979-ffec-4a90-a3e5-a5d1d31c0ee9 doesn't interact with this book"));
    }

    @Test
    public void createMemberRatingInvalidBookId() throws Exception {

        mvc.perform(post("/ratings/createMemberRating")
                        .content(
                                asJsonString(new RatingEntity(4, "user bun",
                                        " ae677979-ffec-4a90-a3e5-a5d1d31c0ee9",
                                        null,"978-0-230-75700-4")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
              //  .andExpect((ResultMatcher) jsonPath("$.message", is("There is an error while executing this test request ")));
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
                .andExpect(status().isBadRequest());
              //  .andExpect(jsonPath("$.message").value("These two users never interact"));
    }

    @Before
    public void createMemberRatingUserCanNotLetRatingsToThemlefs() throws Exception {

        mvc.perform(post("/ratings/createMemberRating")
                        .content(
                                asJsonString(new RatingEntity(4, "user bun",
                                        " ae677979-ffec-4a90-a3e5-a5d1d31c0ee9",
                                        "ae677979-ffec-4a90-a3e5-a5d1d31c0ee9","978-0-230-75700-4")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
             //   .andExpect(jsonPath("$.message").value("Users can not let reviews to themselfs"));
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
