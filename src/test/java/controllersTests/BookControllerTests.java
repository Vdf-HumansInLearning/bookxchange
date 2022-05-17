package controllersTests;

import com.bookxchange.BookExchangeApplication;
import com.bookxchange.controller.BookController;
import com.bookxchange.model.AuthorEntity;
import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.model.BookEntity;
import com.bookxchange.model.MemberEntity;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BookExchangeApplication.class)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
public class BookControllerTests {

    @Autowired
    private BookService workingBookService;

    @Autowired
    private BookMarketService workingBookMarketService;

    @Autowired
    private IsbnService workingIsbnService;

    @InjectMocks
    private BookController workingBookController;

    @Before
    public void createSetup() {
        AuthorEntity authorOne = new AuthorEntity("NameOne", "Surname One");
        AuthorEntity authorTwo = new AuthorEntity("NameTwo", "Surname Two");
        AuthorEntity authorThree = new AuthorEntity("NameThree", "Surname Three");
        AuthorEntity authorFour = new AuthorEntity("NameFour", "Surname Four");

        Set<AuthorEntity> authorsSetOne = new HashSet<AuthorEntity>();
        authorsSetOne.add(authorOne);

        Set<AuthorEntity> authorsSetTwo = new HashSet<AuthorEntity>();
        authorsSetTwo.add(authorTwo);

        Set<AuthorEntity> authorsSetThree = new HashSet<AuthorEntity>();
        authorsSetThree.add(authorThree);
        authorsSetThree.add(authorFour);

        BookEntity bookOne = new BookEntity("1234567890", "Book One Title", "This is book one description 1234567890987654321", 0,authorsSetOne);
        BookEntity bookTwo = new BookEntity("1231231231", "Book Two Title", "This is book two description 1234567890987654321", 0,authorsSetTwo);
        BookEntity bookThree = new BookEntity("0987654321", "Book Three Title", "This is book three description 1234567890987654321", 0,authorsSetThree);

        workingBookService.addNewBookToDB(bookOne);
        workingBookService.addNewBookToDB(bookTwo);
        workingBookService.addNewBookToDB(bookThree);

        MemberEntity newMeberOne = new MemberEntity();
        newMeberOne.setUsername("DanGreen");
        newMeberOne.setPassword("12345");
        newMeberOne.setEmailAddress("green@email.com");
        newMeberOne.setPoints(0);
        newMeberOne.setMemberUserUuid(UUID.randomUUID().toString());

        MemberEntity newMeberTwo = new MemberEntity();
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
