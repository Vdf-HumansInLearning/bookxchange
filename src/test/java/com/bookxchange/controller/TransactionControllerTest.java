package com.bookxchange.controller;

import com.bookxchange.dto.TransactionDTO;
import com.bookxchange.enums.TransactionType;
import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.model.EmailTemplatesEntity;
import com.bookxchange.model.MemberEntity;
import com.bookxchange.model.RoleEntity;
import com.bookxchange.repository.BookMarketRepository;
import com.bookxchange.repository.EmailTemplatesRepository;
import com.bookxchange.repository.MemberRepository;
import com.bookxchange.repository.RoleRepository;
import com.bookxchange.security.JwtTokenUtil;
import com.bookxchange.security.MyUserDetails;
import com.bookxchange.service.EmailTemplatesService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TransactionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    EmailTemplatesService emailTemplatesService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BookMarketRepository bookMarketRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EmailTemplatesRepository emailTemplatesRepository;


    private TransactionDTO transactionDTO = new TransactionDTO(
            "42a48524-20fd-4708-9311-55bf1a247eaf"
            , "null"
            , "ae677979-ffec-4a90-a3e5-a5d1d31c0ee9"
            , "bd15d9b6-abff-4535-96cf-e1e1cffefa24"
            , TransactionType.RENT
    );
    private MemberEntity client = new MemberEntity(
            "bd15d9b6-abff-4535-96cf-e1e1cffefa24"
            , "gabri"
            , 5000
            , "gabriel1vlad@gmail.com"
            , "Parola123@"
    );
    private String token;

    @BeforeEach
    public void setUp() {

        EmailTemplatesEntity emailTemplatesEntity = new EmailTemplatesEntity();
        emailTemplatesEntity.setId(3);
        emailTemplatesEntity.setTemplateName("TRANSACTION_SUCCES");
        emailTemplatesEntity.setSubject("You just made a purchase/rent");
        emailTemplatesEntity.setContentBody("Hey %s , You just made a purchase/rent. Thank you for this.");
        emailTemplatesRepository.save(emailTemplatesEntity);

        System.out.println(emailTemplatesEntity.getId() + "  TEMPLATE ID _____******");
        RoleEntity roleEntity = new RoleEntity(1, "ADMIN");
        roleRepository.save(roleEntity);

        MemberEntity supplier = new MemberEntity();
        supplier.setMemberUserId(1);
        supplier.setMemberUserUuid("ae677979-ffec-4a90-a3e5-a5d1d31c0ee9");
        supplier.setUsername("DanV");
        supplier.setPassword("Parola123@");
        supplier.setPoints(1000);
        supplier.setEmailAddress("dani@gmail.com");
        supplier.setRole(roleEntity);
        memberRepository.save(supplier);
        client.setRole(roleEntity);
        memberRepository.save(client);

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

    @Test
    @WithMockUser
    public void createRentTransaction() throws Exception {

        mockMvc.perform(post("/transactions").header("AUTHORIZATION", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(transactionDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.marketBookIdSupplier").value("42a48524-20fd-4708-9311-55bf1a247eaf"))
                .andExpect(jsonPath("$.memberuuIdFrom").value("ae677979-ffec-4a90-a3e5-a5d1d31c0ee9"))
                .andExpect(jsonPath("$.memberuuIdTo").value("bd15d9b6-abff-4535-96cf-e1e1cffefa24"))
                .andExpect(jsonPath("$.transactionStatus").value("SUCCESS"))
                .andExpect(jsonPath("$.transactionType").value("RENT"));

    }

    @Test
    @WithMockUser
    public void createRentTransactionWithBookStatusRented() throws Exception {

        BookMarketEntity bookMarketEntityRented = new BookMarketEntity();
        bookMarketEntityRented.setBookMarketUuid("495c9b8d-5a71-4215-abe0-71a46e79a02c");
        bookMarketEntityRented.setBookIsbn("0747538492");
        bookMarketEntityRented.setUserUuid("ae677979-ffec-4a90-a3e5-a5d1d31c0ee9");
        bookMarketEntityRented.setForRent((byte) 1);
        bookMarketEntityRented.setForSell((byte) 1);
        bookMarketEntityRented.setSellPrice(100.0);
        bookMarketEntityRented.setBookStatus("RENTED");
        bookMarketRepository.save(bookMarketEntityRented);
        transactionDTO.setMarketBookUuidSupplier("495c9b8d-5a71-4215-abe0-71a46e79a02c");

        mockMvc.perform(post("/transactions").header("AUTHORIZATION", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(transactionDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Book is not available at this time"));

    }

    @Test
    @WithMockUser
    public void createRentTransactionBookMarketIsNotForRent() throws Exception {

        BookMarketEntity bookMarketEntityRented = new BookMarketEntity();
        bookMarketEntityRented.setBookMarketUuid("495c9b8d-5a71-4215-abe0-71a46e79a02c");
        bookMarketEntityRented.setBookIsbn("0747538492");
        bookMarketEntityRented.setUserUuid("ae677979-ffec-4a90-a3e5-a5d1d31c0ee9");
        bookMarketEntityRented.setForRent((byte) 0);
        bookMarketEntityRented.setForSell((byte) 1);
        bookMarketEntityRented.setSellPrice(100.0);
        bookMarketEntityRented.setBookStatus("AVAILABLE");
        bookMarketRepository.save(bookMarketEntityRented);
        transactionDTO.setMarketBookUuidSupplier("495c9b8d-5a71-4215-abe0-71a46e79a02c");

        mockMvc.perform(post("/transactions").header("AUTHORIZATION", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(transactionDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid Transaction conditions"));

    }

    @Test
    @WithMockUser
    public void createSellTransaction() throws Exception {

        transactionDTO.setTransactionType(TransactionType.SELL);

        mockMvc.perform(post("/transactions").header("AUTHORIZATION", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(transactionDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.marketBookIdSupplier").value("42a48524-20fd-4708-9311-55bf1a247eaf"))
                .andExpect(jsonPath("$.memberuuIdFrom").value("ae677979-ffec-4a90-a3e5-a5d1d31c0ee9"))
                .andExpect(jsonPath("$.memberuuIdTo").value("bd15d9b6-abff-4535-96cf-e1e1cffefa24"))
                .andExpect(jsonPath("$.transactionStatus").value("SUCCESS"))
                .andExpect(jsonPath("$.transactionType").value("SELL"));
    }

    @Test
    @WithMockUser
    public void createPointSellTransaction() throws Exception {

        transactionDTO.setTransactionType(TransactionType.POINTSELL);

        mockMvc.perform(post("/transactions").header("AUTHORIZATION", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(transactionDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.marketBookIdSupplier").value("42a48524-20fd-4708-9311-55bf1a247eaf"))
                .andExpect(jsonPath("$.memberuuIdFrom").value("ae677979-ffec-4a90-a3e5-a5d1d31c0ee9"))
                .andExpect(jsonPath("$.memberuuIdTo").value("bd15d9b6-abff-4535-96cf-e1e1cffefa24"))
                .andExpect(jsonPath("$.transactionStatus").value("SUCCESS"))
                .andExpect(jsonPath("$.transactionType").value("POINTSELL"));
    }

    @Test
    @WithMockUser
    public void createTradeTransaction() throws Exception {
        transactionDTO.setMarketBookUuidClient("1ec3d489-9aa0-4cad-8ab3-0ce21a669ddb");
        transactionDTO.setTransactionType(TransactionType.TRADE);

        mockMvc.perform(post("/transactions").header("AUTHORIZATION", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(transactionDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.marketBookIdSupplier").value("42a48524-20fd-4708-9311-55bf1a247eaf"))
                .andExpect(jsonPath("$.memberuuIdFrom").value("ae677979-ffec-4a90-a3e5-a5d1d31c0ee9"))
                .andExpect(jsonPath("$.memberuuIdTo").value("bd15d9b6-abff-4535-96cf-e1e1cffefa24"))
                .andExpect(jsonPath("$.transactionType").value("TRADE"));

    }

    @Test
    @WithMockUser
    public void getTransactionsByUserId() throws Exception {
        transactionDTO.setTransactionType(TransactionType.SELL);

        mockMvc.perform(post("/transactions").header("AUTHORIZATION", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(transactionDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        mockMvc.perform(get("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userID", "bd15d9b6-abff-4535-96cf-e1e1cffefa24"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].transactionType").value("RENT"));

    }

    @Test
    @WithMockUser
    public void getTransactionsByUserIdAndType() throws Exception {

        mockMvc.perform(post("/transactions").header("AUTHORIZATION", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(transactionDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        BookMarketEntity bookMarketEntity1 = new BookMarketEntity();
        bookMarketEntity1.setBookMarketUuid("1c821fb0-1024-4cd0-8f23-2d763fb2c13b");
        bookMarketEntity1.setBookIsbn("0747581088");
        bookMarketEntity1.setUserUuid("ae677979-ffec-4a90-a3e5-a5d1d31c0ee9");
        bookMarketEntity1.setForRent((byte) 1);
        bookMarketEntity1.setForSell((byte) 1);
        bookMarketEntity1.setSellPrice(100.0);
        bookMarketEntity1.setBookStatus("AVAILABLE");
        bookMarketRepository.save(bookMarketEntity1);
        transactionDTO.setMarketBookUuidSupplier("1c821fb0-1024-4cd0-8f23-2d763fb2c13b");

        mockMvc.perform(post("/transactions").header("AUTHORIZATION", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(transactionDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());


        mockMvc.perform(get("/transactions").header("AUTHORIZATION", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userID", "bd15d9b6-abff-4535-96cf-e1e1cffefa24")
                        .param("type)", "RENT"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].transactionType").value("RENT"));

    }

    @AfterEach
    public void delete() {
        memberRepository.deleteAll();
        bookMarketRepository.deleteAll();

    }


}
