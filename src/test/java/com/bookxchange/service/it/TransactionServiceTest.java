package com.bookxchange.service.it;

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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
public class TransactionServiceTest {

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

    private final ObjectMapper objectMapper = new ObjectMapper();

    private TransactionDTO transactionDTO=new TransactionDTO(
            "42a48524-20fd-4708-9311-55bf1a247eaf"
            ,"null"
            ,"ae677979-ffec-4a90-a3e5-a5d1d31c0ee9"
            ,"bd15d9b6-abff-4535-96cf-e1e1cffefa24"
            , TransactionType.RENT
    );

    @BeforeEach
    public void setUp() throws Exception {
        EmailTemplatesEntity emailTemplatesEntity = new EmailTemplatesEntity();
        emailTemplatesEntity.setId(4);
        emailTemplatesEntity.setTemplateName("TRANSACTION_SUCCES");
        emailTemplatesEntity.setSubject("You just made a purchase/rent");
        emailTemplatesEntity.setContentBody("Hey %s , You just made a purchase/rent. Thank you for this.");
        emailTemplatesRepository.save(emailTemplatesEntity);

        RoleEntity roleEntity = new RoleEntity(1, "ADMIN");
        roleRepository.save(roleEntity);
        MemberEntity supplier = new MemberEntity();
        supplier.setMemberUserId(1);
        supplier.setMemberUserUuid("ae677979-ffec-4a90-a3e5-a5d1d31c0ee9");
        supplier.setUsername("DanV");
        supplier.setPassword("Parola123@");
        supplier.setPoints(100);
        supplier.setEmailAddress("dani@gmail.com");
        supplier.setRole(roleEntity);
        memberRepository.save(supplier);

        MemberEntity client = new MemberEntity();
        client.setMemberUserId(2);
        client.setMemberUserUuid("bd15d9b6-abff-4535-96cf-e1e1cffefa24");
        client.setUsername("gabri");
        client.setPassword("Parola123@");
        client.setPoints(200);
        client.setEmailAddress("gabriel1vlad@gmail.com");
        client.setRole(roleEntity);
        memberRepository.save(client);

        BookMarketEntity bookMarketEntity = new BookMarketEntity();
        bookMarketEntity.setBookMarketUuid("42a48524-20fd-4708-9311-55bf1a247eaf");
        bookMarketEntity.setBookIsbn("0747532699");
        bookMarketEntity.setUserUuid("ae677979-ffec-4a90-a3e5-a5d1d31c0ee9");
        bookMarketEntity.setForRent((byte) 1);
        bookMarketEntity.setForSell((byte) 1);
        bookMarketEntity.setBookStatus("AVAILABLE");
        bookMarketRepository.save(bookMarketEntity);

    }

    @Test
    public void createRentTransaction() throws Exception {

        mockMvc.perform(post("/transactions").header("AUTHORIZATION", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnYWJyaSIsInVzZXJVVUlEIjoiYmQxNWQ5YjYtYWJmZi00NTM1LTk2Y2YtZTFlMWNmZmVmYTI0Iiwic2NvcGVzIjoiQURNSU4iLCJleHAiOjE2NTI3OTc5NzksImlhdCI6MTY1Mjc3OTk3OX0.VRryR0lCaSuiSKjR9PuXsL2msaTYnbQC1BFSkHE09jgdTPyI6k2cNJ7yiYBcj4MrRTN9vRM8DTk4Hsl2osJiCg")
                        .content(objectMapper.writeValueAsString(transactionDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.transactionType").value("RENT"));
    }

    @Test
    public void createSellTransaction() throws Exception {

        transactionDTO.setTransactionType(TransactionType.SELL);

        mockMvc.perform(post("/transactions").header("AUTHORIZATION", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnYWJyaSIsInVzZXJVVUlEIjoiYmQxNWQ5YjYtYWJmZi00NTM1LTk2Y2YtZTFlMWNmZmVmYTI0Iiwic2NvcGVzIjoiQURNSU4iLCJleHAiOjE2NTI3OTc5NzksImlhdCI6MTY1Mjc3OTk3OX0.VRryR0lCaSuiSKjR9PuXsL2msaTYnbQC1BFSkHE09jgdTPyI6k2cNJ7yiYBcj4MrRTN9vRM8DTk4Hsl2osJiCg")
                        .content(objectMapper.writeValueAsString(transactionDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.transactionType").value("SELL"));
    }
    @AfterEach
    public void tear(){
        memberRepository.deleteAll();
        bookMarketRepository.deleteAll();

    }



}
