package com.bookxchange.controller;

import com.bookxchange.BookExchangeApplication;
import com.bookxchange.model.*;
import com.bookxchange.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BookExchangeApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class NotificationsControllerTest {
    @Autowired
    BookMarketRepository bookMarketRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AuthorsRepository authorsRepository;
    @Autowired
    BooksRepository booksRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private MockMvc mvc;
    private String marketBookIdTest;
    private String memberIdTest;

    @BeforeEach
    public void createNewBookMarket() {

        RoleEntity role = new RoleEntity();
        role.setRoleId(1);
        role.setRoleName("ADMIN");
        roleRepository.save(role);


        MemberEntity membersEntity = new MemberEntity();
        membersEntity.setMemberUserId(1);
        membersEntity.setEmailAddress("test@gmail.com");
        membersEntity.setMemberUserUuid("1234-1244");
        membersEntity.setUsername("name");
        membersEntity.setPoints(0);
        membersEntity.setRole(role);
        memberRepository.save(membersEntity);

        memberIdTest = membersEntity.getMemberUserUuid();

        AuthorEntity authors = new AuthorEntity();
        authors.setAuthorsUuid("2124-24124-5332");
        authors.setName("Test");
        authors.setSurname("Testica");
        authorsRepository.save(authors);

        BookEntity booksEntity = new BookEntity();
        booksEntity.setIsbn("1234567891");
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
