package EmailServiceTest;

import com.bookxchange.service.EmailService;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppEmailConfig.class)
public class EmailTest {

    @Resource
    private EmailService emailSender;

    private GreenMail testSmtp;

    @Before
    public void testSmtpInit(){
        testSmtp = new GreenMail(ServerSetupTest.SMTP);
        testSmtp.start();

        JavaMailSenderImpl impl = new JavaMailSenderImpl();
        impl.setPort(3025);
        impl.setHost("localhost");
        emailSender = new EmailService(impl);
    }

    @Test
    public void testEmail() throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("mafiacartilor@gmail.com");
        message.setTo("test@receiver.com");
        message.setSubject("test subject");
        message.setText("test message");
        emailSender.sendMail(message.getTo()[0],message.getSubject(),message.getText());

        Message[] messages = testSmtp.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("test subject", messages[0].getSubject());
        String body = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
        assertEquals("test message", body);
    }

    @After
    public void cleanup(){
        testSmtp.stop();
    }
}