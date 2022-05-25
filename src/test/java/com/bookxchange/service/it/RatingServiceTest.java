package com.bookxchange.service.it;

import com.bookxchange.BookExchangeApplication;
import com.bookxchange.dto.RatingDTO;
import com.bookxchange.dto.TransactionDTO;
import com.bookxchange.enums.TransactionType;
import com.bookxchange.model.*;
import com.bookxchange.repository.BookMarketRepository;
import com.bookxchange.repository.MemberRepository;
import com.bookxchange.repository.RoleRepository;
import com.bookxchange.repository.TransactionRepository;
import com.bookxchange.security.JwtTokenUtil;
import com.bookxchange.security.MyUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {BookExchangeApplication.class})
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class RatingServiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mvc;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BookMarketRepository bookMarketRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    private String token;

    private MemberEntity client = new MemberEntity(
            "bd15d9b6-abff-4535-96cf-e1e1cffefa24"
            , "gabri"
            , 5000
            , "gabriel1vlad@gmail.com"
            , "Parola123@"
    );
    private MemberEntity supplier = new MemberEntity(
            "6eca21ce-861b-4dd7-975d-20a969e3183a"
            , "mihai"
            , 5000
            , "melcuul@gmail.com"
            , "Parola123@"
    );

    private RatingDTO ratingDTO = new RatingDTO(4
            , "good user"
            , "bd15d9b6-abff-4535-96cf-e1e1cffefa24"
            , "13177e99-14b5-43c5-a446-e0dc751c3153"
            , "null"
    );

    private TransactionDTO transactionDTO = new TransactionDTO(
            "42a48524-20fd-4708-9311-55bf1a247eaf"
            , "null"
            , "6eca21ce-861b-4dd7-975d-20a969e3183a"
            , "bd15d9b6-abff-4535-96cf-e1e1cffefa24"
            , TransactionType.RENT
    );

    @BeforeEach
    public void createRentTransaction() throws Exception {

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionType("SELL");
        transactionEntity.setMarketBookIdSupplier("42a48524-20fd-4708-9311-55bf1a247eaf");
        transactionEntity.setMemberuuIdFrom("6eca21ce-861b-4dd7-975d-20a969e3183a");
        transactionEntity.setMemberuuIdTo("bd15d9b6-abff-4535-96cf-e1e1cffefa24");
        transactionEntity.setTransactionDate(LocalDate.now());
        transactionEntity.setTransactionStatus("SUCCESS");
        System.out.println(transactionEntity+ " TRANSACTIE");
        transactionRepository.save(transactionEntity);
        List<TransactionEntity> a = transactionRepository.getTransactionByWhoSelleddAndWhoBuys("6eca21ce-861b-4dd7-975d-20a969e3183a", "bd15d9b6-abff-4535-96cf-e1e1cffefa24");
        System.out.println(a + "LISTA");
        RoleEntity roleEntity = new RoleEntity(1, "ADMIN");
        roleRepository.save(roleEntity);
        client.setRole(roleEntity);
        supplier.setRole(roleEntity);
        memberRepository.save(client);
        memberRepository.save(supplier);
        token = jwtTokenUtil.generateToken(new MyUserDetails(client) {
        });

        BookMarketEntity bookMarketEntity = new BookMarketEntity();
        bookMarketEntity.setBookMarketUuid("42a48524-20fd-4708-9311-55bf1a247eaf");
        bookMarketEntity.setBookIsbn("0747532699");
        bookMarketEntity.setUserUuid("ae677979-ffec-4a90-a3e5-a5d1d31c0ee9");
        bookMarketEntity.setForRent((byte) 1);
        bookMarketEntity.setForSell((byte) 1);
        bookMarketEntity.setSellPrice(100.0);
        bookMarketEntity.setBookStatus("AVAILABLE");
        bookMarketRepository.save(bookMarketEntity);

    }


    @org.junit.jupiter.api.Test
    @WithMockUser
    public void createMemberRatingUserNeverInteract() throws Exception {
        mvc.perform(post("/ratings/member").header("AUTHORIZATION", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(ratingDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @org.junit.jupiter.api.Test
    @WithMockUser
    public void createBookRatingInvalidBookId() throws Exception {

        mvc.perform(post("/ratings/book").header("AUTHORIZATION", "Bearer " + token)
                        .content("{\n" +
                                "   \"grade\": 2, \n" +
                                "   \"description\":\"ceva\",\n" +
                                "   \"leftBy\":\"ae677979-ffec-4a90-a3e5-a5d1d31c0ee9\",\n" +
                                "   \"userId\": \"ae677979-ffec-4a90-a3e5-a5d1d31c0ee7\",\n" +
                                "   \"bookId\":" + null +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Book id can not be null when you rate a book"));
    }

    @org.junit.jupiter.api.Test
    @WithMockUser
    public void createBookRatingUsersNeverInteract() throws Exception {

        mvc.perform(post("/ratings/book").header("AUTHORIZATION", "Bearer " + token)
                        .content("{\n" +
                                "   \"grade\": 2, \n" +
                                "   \"description\":\"ceva\",\n" +
                                "   \"leftBy\":\"ae677979-ffec-4a90-a3e5-a5d1d31c0ee9\",\n" +
                                "   \"userId\": \"ae677979-ffec-4a90-a3e5-a5d1d31c0ee7\",\n" +
                                "   \"bookId\": \"978-0-230-75700-3\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("This user bd15d9b6-abff-4535-96cf-e1e1cffefa24 doesn't interact with this book"));
    }

    @org.junit.jupiter.api.Test
    @WithMockUser
    public void createMemberRatingInvalidUserId() throws Exception {

        mvc.perform(post("/ratings/member").header("AUTHORIZATION", "Bearer " + token)
                        .content(
                                objectMapper.writeValueAsString(new RatingEntity(4, "user bun",
                                        " ae677979-ffec-4a90-a3e5-a5d1d31c0ee9",
                                        null, "978-0-230-75700-4")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User id can not be null when you rate a member"));
    }

    @org.junit.jupiter.api.Test
    @WithMockUser
    public void createMemberRating() throws Exception {

        mvc.perform(post("/ratings/member").header("AUTHORIZATION", "Bearer " + token)
                        .content("{\n" +
                                "   \"grade\": 4, \n" +
                                "   \"description\":\"ceva\",\n" +
                                "   \"leftBy\":\"ae677979-ffec-4a90-a3e5-a5d1d31c0ee9\",\n" +
                                "   \"userId\":\"6eca21ce-861b-4dd7-975d-20a969e3183a\",\n" +
                                "   \"bookId\": null\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @org.junit.jupiter.api.Test
    @WithMockUser
    public void createMemberRatingUserCanNotLetRatingsToThemselves() throws Exception {
        ratingDTO.setUserId(client.getMemberUserUuid());

        mvc.perform(post("/ratings/member").header("AUTHORIZATION", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(ratingDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Users can not let reviews to themselves"));
    }

    @AfterEach
    public void contextLoads() {
        transactionRepository.deleteAll();
        memberRepository.deleteAll();
        bookMarketRepository.deleteAll();
    }


}
