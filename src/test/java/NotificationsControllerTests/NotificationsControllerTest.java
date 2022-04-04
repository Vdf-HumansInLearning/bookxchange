package NotificationsControllerTests;

import com.bookxchange.BookExchangeApplication;
import com.bookxchange.model.*;
import com.bookxchange.repositories.*;
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
    BooksRepository booksRepository;


    private String marketBookIdTest;
    private String memberIdTest;

    @Before
    public void createNewBookMarket() {



        MembersEntity membersEntity = new MembersEntity();
        membersEntity.setEmailAddress("test@gmail.com");
        membersEntity.setMemberUserUuid("1234-1244");
        membersEntity.setUsername("name");
        membersEntity.setPoints(0);
        memberRepository.save(membersEntity);

        memberIdTest = membersEntity.getMemberUserUuid();

        AuthorsEntity authors = new AuthorsEntity();
        authors.setAuthorsUuid("2124-24124-5332");
        authors.setName("Test name");
        authors.setSurname("Test surname");
        authorsRepository.save(authors);

        BooksEntity booksEntity = new BooksEntity();
        booksEntity.setIsbn("testisbn");
        booksEntity.setTitle("Test title");
        booksEntity.setDescription("test descript");
        booksEntity.setQuantity(1);
        booksRepository.save(booksEntity);


        BookMarketEntity bookMarketEntity = new BookMarketEntity();
        bookMarketEntity.setUserUuid(membersEntity.getMemberUserUuid());
        bookMarketEntity.setBookIsbn(booksEntity.getIsbn());
        bookMarketEntity.setBookMarketUuid("1223-34534-6343-3222");
        bookMarketEntity.setBookState("ORIGINALBOX");
        bookMarketEntity.setForSell(Integer.valueOf(0).byteValue());
        bookMarketEntity.setSellPrice(0d);
        bookMarketEntity.setForRent(Integer.valueOf(1).byteValue());
        bookMarketEntity.setRentPrice(200d);
        bookMarketEntity.setBookStatus("RENTED");
        bookMarketRepository.save(bookMarketEntity);

        marketBookIdTest = bookMarketEntity.getBookMarketUuid();
    }

    @Test
    public void addNotification() throws Exception {

        mvc.perform(post("/notifications")
                        .content("{\n" +
                                "  \"marketBookUuid\": \"" + marketBookIdTest + "\",\n" +
                                "  \"memberUuid\": \"" + memberIdTest + "\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.marketBookUuid").value("1223-34534-6343-3222"))
                .andExpect(jsonPath("$.memberUuid").value("1234-1244"))
                .andExpect(jsonPath("$.sent").value("0"))
                .andExpect(jsonPath("$.templateType").value("1"));
    }

    @Test
    public void addNotificationDuplicate() throws Exception {

        mvc.perform(post("/notifications")
                        .content("{\n" +
                                "  \"marketBookUuid\": \"" + marketBookIdTest + "\",\n" +
                                "  \"memberUuid\": \"" + memberIdTest + "\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.marketBookUuid").value(marketBookIdTest))
                .andExpect(jsonPath("$.memberUuid").value(memberIdTest))
                .andExpect(jsonPath("$.sent").value("0"))
                .andExpect(jsonPath("$.templateType").value("1"));

        mvc.perform(post("/notifications")
                        .content("{\n" +
                                "  \"marketBookUuid\": \"" + marketBookIdTest + "\",\n" +
                                "  \"memberUuid\": \"" + memberIdTest + "\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Duplicate Notification"));
    }



}
