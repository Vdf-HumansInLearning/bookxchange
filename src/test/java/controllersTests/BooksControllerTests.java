package controllersTests;

import com.bookxchange.BookExchangeApplication;
import com.bookxchange.controller.BooksController;
import com.bookxchange.model.AuthorsEntity;
import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.model.BooksEntity;
import com.bookxchange.model.MembersEntity;
import com.bookxchange.service.BookMarketService;
import com.bookxchange.service.BookService;
import com.bookxchange.service.IsbnService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.springframework.security.config.http.MatcherType.mvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BookExchangeApplication.class)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
public class BooksControllerTests {

    @Autowired
    private BookService workingBookService;

    @Autowired
    private BookMarketService workingBookMarketService;

    @Autowired
    private IsbnService workingIsbnService;

    @InjectMocks
    private BooksController workingBooksController;

    @Before
    public void createSetup() {
        AuthorsEntity authorOne = new AuthorsEntity("NameOne", "Surname One");
        AuthorsEntity authorTwo = new AuthorsEntity("NameTwo", "Surname Two");
        AuthorsEntity authorThree = new AuthorsEntity("NameThree", "Surname Three");
        AuthorsEntity authorFour = new AuthorsEntity("NameFour", "Surname Four");

        Set<AuthorsEntity> authorsSetOne = new HashSet<AuthorsEntity>();
        authorsSetOne.add(authorOne);

        Set<AuthorsEntity> authorsSetTwo = new HashSet<AuthorsEntity>();
        authorsSetTwo.add(authorTwo);

        Set<AuthorsEntity> authorsSetThree = new HashSet<AuthorsEntity>();
        authorsSetThree.add(authorThree);
        authorsSetThree.add(authorFour);

        BooksEntity bookOne = new BooksEntity("1234567890", "Book One Title", "This is book one description 1234567890987654321", 0,authorsSetOne);
        BooksEntity bookTwo = new BooksEntity("1231231231", "Book Two Title", "This is book two description 1234567890987654321", 0,authorsSetTwo);
        BooksEntity bookThree = new BooksEntity("0987654321", "Book Three Title", "This is book three description 1234567890987654321", 0,authorsSetThree);

        workingBookService.addNewBookToDB(bookOne);
        workingBookService.addNewBookToDB(bookTwo);
        workingBookService.addNewBookToDB(bookThree);

        MembersEntity newMeberOne = new MembersEntity();
        newMeberOne.setUsername("DanGreen");
        newMeberOne.setPassword("12345");
        newMeberOne.setEmailAddress("green@email.com");
        newMeberOne.setPoints(0);
        newMeberOne.setMemberUserUuid(UUID.randomUUID().toString());

        MembersEntity newMeberTwo = new MembersEntity();
        newMeberOne.setUsername("BioAlin");
        newMeberOne.setPassword("12345");
        newMeberOne.setEmailAddress("bio@email.com");
        newMeberOne.setPoints(0);
        newMeberOne.setMemberUserUuid(UUID.randomUUID().toString());

        BookMarketEntity newBookMarketEntityOne = new BookMarketEntity();
        newBookMarketEntityOne.setBookMarketUuid(UUID.randomUUID().toString());
        newBookMarketEntityOne.setBookIsbn("1234567890");
        newBookMarketEntityOne.setBookState("ASNEW");
        newBookMarketEntityOne.setBookStatus("AVAILABLE");
        newBookMarketEntityOne.setForRent((byte) 1);
        newBookMarketEntityOne.setRentPrice(9.99d);
        newBookMarketEntityOne.setForSell((byte) 0);
        newBookMarketEntityOne.setSellPrice(0.00d);
        newBookMarketEntityOne.setUserUuid(newMeberOne.getMemberUserUuid());

        BookMarketEntity newBookMarketEntityTwo = new BookMarketEntity();
        newBookMarketEntityTwo.setBookMarketUuid(UUID.randomUUID().toString());
        newBookMarketEntityTwo.setBookIsbn("1231231231");
        newBookMarketEntityTwo.setBookState("USED");
        newBookMarketEntityTwo.setBookStatus("AVAILABLE");
        newBookMarketEntityTwo.setForRent((byte) 1);
        newBookMarketEntityTwo.setRentPrice(9.99d);
        newBookMarketEntityTwo.setForSell((byte) 1);
        newBookMarketEntityTwo.setSellPrice(19.99d);
        newBookMarketEntityTwo.setUserUuid(newMeberOne.getMemberUserUuid());

        BookMarketEntity newBookMarketEntityThree = new BookMarketEntity();
        newBookMarketEntityThree.setBookMarketUuid(UUID.randomUUID().toString());
        newBookMarketEntityThree.setBookIsbn("0987654321");
        newBookMarketEntityThree.setBookState("USED");
        newBookMarketEntityThree.setBookStatus("AVAILABLE");
        newBookMarketEntityThree.setForRent((byte) 0);
        newBookMarketEntityThree.setRentPrice(9.00d);
        newBookMarketEntityThree.setForSell((byte) 1);
        newBookMarketEntityThree.setSellPrice(19.99d);
        newBookMarketEntityThree.setUserUuid(newMeberTwo.getMemberUserUuid());


        workingBookMarketService.addBookMarketEntry(newBookMarketEntityOne);
        workingBookMarketService.addBookMarketEntry(newBookMarketEntityTwo);
        workingBookMarketService.addBookMarketEntry(newBookMarketEntityThree);


    }

    @Test
    public void checkForABookBasedPresnetInTheDataBaseByIsbnAndReturnDetails() {
//
//        mvc.perform(get("/books//getBookDetailsISBN"))
//                .content("{\n" +
//                        "   \"retrievedInfo\":\""")
//                .contentType(MediaType.APPLICATION_JSON))
//        .andExpect(status().isOK))
//        .andExpect(jsonPath("$.retrievedInfo").value(true));
//
    }



}
