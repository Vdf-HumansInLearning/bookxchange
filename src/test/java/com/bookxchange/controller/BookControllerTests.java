package com.bookxchange.controller;

import com.bookxchange.model.*;
import com.bookxchange.repository.BookMarketRepository;
import com.bookxchange.repository.MemberRepository;
import com.bookxchange.repository.RoleRepository;
import com.bookxchange.security.JwtTokenUtil;
import com.bookxchange.security.MyUserDetails;
import com.bookxchange.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class BookControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private BookService workingBookService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BookMarketRepository bookMarketRepository;
    @Autowired
    private MemberRepository memberRepository;

    private String token;

    @BeforeEach
    public void createSetup() {

        RoleEntity roleEntity = new RoleEntity(1, "ADMIN");
        roleRepository.save(roleEntity);
        AuthorEntity authorOne = new AuthorEntity("NameOne", "Surname One");
        AuthorEntity authorTwo = new AuthorEntity("NameTwo", "Surname Two");
        AuthorEntity authorThree = new AuthorEntity("NameThree", "Surname Three");
        AuthorEntity authorFour = new AuthorEntity("NameFour", "Surname Four");

        ArrayList<AuthorEntity> authorsSetOne = new ArrayList<>();
        authorsSetOne.add(authorOne);

        ArrayList<AuthorEntity> authorsSetTwo = new ArrayList<>();
        authorsSetTwo.add(authorTwo);

        ArrayList<AuthorEntity> authorsSetThree = new ArrayList<>();
        authorsSetThree.add(authorThree);
        authorsSetThree.add(authorFour);

        BookEntity bookOne = new BookEntity("1234567890", "Book One Title", "This is book one description 1234567890987654321", 0, authorsSetOne);
        BookEntity bookTwo = new BookEntity("1231231231", "Book Two Title", "This is book two description 1234567890987654321", 0, authorsSetTwo);
        BookEntity bookThree = new BookEntity("0987654321", "Book Three Title", "This is book three description 1234567890987654321", 0, authorsSetThree);

        workingBookService.addNewBookToDB(bookOne);
        workingBookService.addNewBookToDB(bookTwo);
        workingBookService.addNewBookToDB(bookThree);

        MemberEntity newMeberOne = new MemberEntity();
        newMeberOne.setUsername("DanGreen");
        newMeberOne.setPassword("Parola123@");
        newMeberOne.setEmailAddress("green@email.com");
        newMeberOne.setPoints(0);
        newMeberOne.setRole(roleEntity);
        newMeberOne.setMemberUserUuid(UUID.randomUUID().toString());
        memberRepository.save(newMeberOne);
        token = jwtTokenUtil.generateToken(new MyUserDetails(newMeberOne) {
        });
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
        bookMarketRepository.save(newBookMarketEntityOne);

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
        bookMarketRepository.save(newBookMarketEntityTwo);

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
        bookMarketRepository.save(newBookMarketEntityThree);


    }

    @Test
    @WithMockUser
    public void getBookByIsbn() throws Exception {

        mockMvc.perform(get("/books/isbn").header("AUTHORIZATION", "Bearer " + token)
                        .param("isbn", "1234567890")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }


}
