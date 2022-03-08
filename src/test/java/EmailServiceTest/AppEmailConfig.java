package EmailServiceTest;

import com.bookxchange.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class AppEmailConfig {
    @Bean
    public EmailService emailSender(){
        JavaMailSenderImpl impl = new JavaMailSenderImpl();
        impl.setHost("smtp.gmail.com");
        impl.setPort(587);
        impl.setUsername("mafiacartilor@gmail.com");
        impl.setPassword("umudnbmzyvyarzla");
        Properties mailProps = new Properties();
        mailProps.setProperty("mail.smtp.auth","true");
        mailProps.setProperty("mail.smtp.starttls.enable","true");
        impl.setJavaMailProperties(mailProps);
        return new EmailService(impl);
    }
}
