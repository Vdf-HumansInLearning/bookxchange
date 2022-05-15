package com.bookxchange.controller;

import com.bookxchange.BookExchangeApplication;
import com.bookxchange.dto.TransactionDto;
import com.bookxchange.enums.TransactionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK, classes={ BookExchangeApplication.class })
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionController transactionController;

    private TransactionDto transactionDto = new TransactionDto(
            "42a48524-20fd-4708-9311-55bf1a247eaf"
            , "1ec3d489-9aa0-4cad-8ab3-0ce21a669ddb"
            , "ae677979-ffec-4a90-a3e5-a5d1d31c0ee9"
            , "6eca21ce-861b-4dd7-975d-20a969e3183a"
            , TransactionType.SELL
    );


    private ObjectMapper objectMapper = new ObjectMapper();


    @Before
    public void setUp() throws Exception {

    }
//TODO: Nu reusesc sa integrez tokenul jwt in mvc perfom post :(
    @Test
    void createRentTransaction() throws Exception {
        mockMvc.perform(post("/transactions")
                .content(
                        "{\n" +
                        "  \"marketBookIdSupplier\": \"42a48524-20fd-4708-9311-55bf1a247eaf\",\n" +
                        "  \"supplierId\": \"ae677979-ffec-4a90-a3e5-a5d1d31c0ee9\",\n" +
                        "  \"clientId\": \"6eca21ce-861b-4dd7-975d-20a969e3183a\",\n" +
                        "  \"transactionType\": \"RENT\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
//
//    @Test
//    void createSellTransaction() {
//    }
//
//    @Test
//    void createSellWithPointsTransaction() {
//    }
//
//    @Test
//    void createTradeTransaction() {
//    }
//
//    @Test
//    void getTransactionsByMemberUuIDAndType() {
//    }
//
//    @Test
//    void approveTradingTransaction() {
//    }
}