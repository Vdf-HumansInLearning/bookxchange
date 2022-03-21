package NotificationsControllerTests;

import com.bookxchange.BookExchangeApplication;
import com.bookxchange.model.*;
import com.bookxchange.repositories.*;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BookExchangeApplication.class)
@AutoConfigureMockMvc
@Transactional
public class NotificationsControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    BookMarketRepository bookMarketRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AuthorsRepository authorsRepository;

    @Autowired
    BookRepository bookRepository;


    UUID bookMarketId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    UUID author = UUID.randomUUID();
    String bookIsbn = "12345";

    String marketBookId = "b9861c66-fb3f-416f-b00a-59cbc521314d";
    String memberId = "ae677979-ffec-4a90-a3e5-a5d1d31c0ee9";

    @Before
    public void createNewBookMarket() {

        /*System.out.println("user" + userId.toString());
        System.out.println("bookmarket" + bookMarketId.toString());
        System.out.println("author" + author.toString());


        AuthorsEntity authors = new AuthorsEntity();
        authors.setId(author.toString());
        authors.setName("Test name");
        authors.setSurname("Test surname");
        authorsRepository.save(authors);

        BooksEntity booksEntity = new BooksEntity();
        booksEntity.setIsbn(bookIsbn);
        booksEntity.setTitle("Test title");
        booksEntity.setAuthor(author.toString());
        booksEntity.setDescription("test descript");
        booksEntity.setQuantity(1);
        bookRepository.save(booksEntity);


        BookMarketEntity bookMarketEntity = new BookMarketEntity();
        bookMarketEntity.setBookId(bookMarketId.toString());
        bookMarketEntity.setUserId(userId.toString());
        bookMarketEntity.setBookId(bookIsbn);
        bookMarketEntity.setBookState("ORIGINALBOX");
        bookMarketEntity.setForSell(Integer.valueOf(0).byteValue());
        bookMarketEntity.setSellPrice(0d);
        bookMarketEntity.setForRent(Integer.valueOf(1).byteValue());
        bookMarketEntity.setRentPrice(200d);
        bookMarketEntity.setBookStatus("RENTED");
        bookMarketRepository.save(bookMarketEntity);*/
    }

    @Test
    public void addNotification() throws Exception {

        mvc.perform(post("/notifications")
                        .content("{\n" +
                                "  \"marketBookId\": \"" + marketBookId + "\",\n" +
                                "  \"memberId\": \"" + memberId + "\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.marketBookId").value(marketBookId))
                .andExpect(jsonPath("$.memberId").value(memberId))
                .andExpect(jsonPath("$.sent").value("0"))
                .andExpect(jsonPath("$.templateType").value("1"));
    }

    @Test
    public void addNotificationDuplicate() throws Exception {

        mvc.perform(post("/notifications")
                        .content("{\n" +
                                "  \"marketBookId\": \"" + marketBookId + "\",\n" +
                                "  \"memberId\": \"" + memberId + "\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.marketBookId").value(marketBookId))
                .andExpect(jsonPath("$.memberId").value(memberId))
                .andExpect(jsonPath("$.sent").value("0"))
                .andExpect(jsonPath("$.templateType").value("1"));

        mvc.perform(post("/notifications")
                        .content("{\n" +
                                "  \"marketBookId\": \"" + marketBookId + "\",\n" +
                                "  \"memberId\": \"" + memberId + "\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Duplicate Notification"));
    }



}
