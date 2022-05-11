package NotificationsControllerTests;

import com.bookxchange.BookExchangeApplication;
import com.bookxchange.controller.NotificationController;
import com.bookxchange.dto.NotificationsDTO;
import com.bookxchange.model.NotificationsEntity;
import com.bookxchange.repositories.*;
import com.bookxchange.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BookExchangeApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")

@Transactional
public class NotificationsControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    NotificationController notificationController;

    @MockBean
    NotificationRepository notificationRepository;

    @MockBean
    BookMarketRepository bookMarketRepository;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    AuthorsRepository authorsRepository;

    @MockBean
    BooksRepository booksRepository;

    @MockBean
    NotificationService notificationService;

    private ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @Test
    public void testNotification() throws Exception {
        NotificationsDTO notificationsDTO = new NotificationsDTO();
        notificationsDTO.setMarketBookUuid("1223-34534-6343-3222");
        notificationsDTO.setMemberUuid("1234-1244");
        Byte b = 1;

        NotificationsEntity notificationsEntity = new NotificationsEntity();
        notificationsEntity.setSent(b);
        notificationsEntity.setMemberUuid("1234-1244");
        notificationsEntity.setMarketBookUuid("1223-34534-6343-3222");
        notificationsEntity.setTemplateType(1);

        when(notificationService.addNotification(notificationsDTO.getMarketBookUuid(),notificationsDTO.getMemberUuid()))
                .thenReturn(notificationsEntity);

        mvc.perform(post("/notifications")
                        .content(objectMapper.writeValueAsBytes(notificationsDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.marketBookUuid").value("1223-34534-6343-3222"))
                .andExpect(jsonPath("$.memberUuid").value("1234-1244"))
                .andExpect(jsonPath("$.sent").value("1"))
                .andExpect(jsonPath("$.templateType").value("1"));



    }



}
