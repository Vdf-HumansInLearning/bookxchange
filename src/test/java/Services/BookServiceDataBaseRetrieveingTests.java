package Services;

import com.bookxchange.BookExchangeApplication;
import com.bookxchange.model.AuthorsEntity;
import com.bookxchange.model.BooksEntity;
import com.bookxchange.repositories.BooksRepository;
import com.bookxchange.service.AuthorsService;
import com.bookxchange.service.BookService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BookExchangeApplication.class)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
public class BookServiceDataBaseRetrieveingTests {

    @Autowired
    private BooksRepository bookRepo;

    @Autowired
    private AuthorsService workingAuthorService;

    @Autowired
    private BookService workingBookService;

    @Before
     public void setUpTestData(){
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

       bookRepo.save(bookOne);
       bookRepo.save(bookTwo);
       bookRepo.save(bookThree);
   }

   @Test
   public void addABookToTheDataBaseWithNewAuthor() {
       AuthorsEntity authorFive = new AuthorsEntity("NameFive", "Surname Five");
       Set<AuthorsEntity> authorsSetFour = new HashSet<AuthorsEntity>();
       authorsSetFour.add(authorFive);

       BooksEntity bookFour = new BooksEntity("9879879871", "Book Four Title", "This is book four description 1234567890987654321", 0,authorsSetFour);

       workingBookService.addNewBookToDB(bookFour);

       BooksEntity savedBook = bookRepo.getByIsbn("9879879871");

       Assert.assertNotNull(savedBook);
       Assert.assertTrue(savedBook.getAuthors().contains(authorFive));
   }

   @Test
   public void CheckThatOnlyOneAuthorIsPressentInTheDataBaseIfABookWithExistingAuthorIsAdded() {
       AuthorsEntity authorFive = new AuthorsEntity("NameFive", "Surname Five");
       Set<AuthorsEntity> authorsSetFive = new HashSet<AuthorsEntity>();
       authorsSetFive.add(authorFive);

       BooksEntity bookFive = new BooksEntity("4564564561", "Book Five Title", "This is book five description 1234567890987654321", 0,authorsSetFive);
       bookRepo.save(bookFive);

       Assert.assertEquals(1, workingAuthorService.getAuthorCountFromDataBaseFullName("NameFive", "Surname Five"));
   }

   @Test
   public void userGetsABookBasedOnISBN() {
        assertNotNull(workingBookService.retrieveBookFromDB("1234567890"));
   }

    @Test
    public void userGetsAllBooksInTheDataBase() {
        Assert.assertTrue(0<workingBookService.userRetrievesBookList().size());
    }

    @Test
    public void userAddsABook() {
        AuthorsEntity authorSix = new AuthorsEntity("NameSix", "Surname Six");
        Set<AuthorsEntity> authorsSetSix = new HashSet<AuthorsEntity>();
        authorsSetSix.add(authorSix);

        BooksEntity bookSix = new BooksEntity("4564564561", "Book Five Title", "This is book five description 1234567890987654321", 0,authorsSetSix);

        workingBookService.addNewBookToDB(bookSix);
        Assert.assertEquals(bookSix, workingBookService.retrieveBookFromDB(bookSix.getIsbn()));
    }

}
