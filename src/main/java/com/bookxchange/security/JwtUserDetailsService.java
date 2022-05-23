package com.bookxchange.security;

import com.bookxchange.dto.RegisterDTO;
import com.bookxchange.enums.UserRoles;
import com.bookxchange.exception.BadAuthentificationException;
import com.bookxchange.model.MemberEntity;
import com.bookxchange.model.RoleEntity;
import com.bookxchange.service.EmailService;
import com.bookxchange.service.EmailTemplatesService;
import com.bookxchange.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    MemberService memberService;
    EmailService emailService;
    EmailTemplatesService emailTemplatesService;

    Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

    @Autowired
    public JwtUserDetailsService(MemberService memberService, EmailService emailService, EmailTemplatesService emailTemplatesService) {
        this.memberService = memberService;
        this.emailService = emailService;
        this.emailTemplatesService = emailTemplatesService;
    }

    public static boolean isValidPassword(String password) {


//        ^ represents starting character of the string.
//        (?=.*[0-9]) represents a digit must occur at least once.
//        (?=.*[a-z]) represents a lower case alphabet must occur at least once.
//        (?=.*[A-Z]) represents an upper case alphabet that must occur at least once.
//        (?=.*[@#$%^&-+=()] represents a special character that must occur at least once.
//        (?=\\S+$) white spaces don’t allowed in the entire string.
//.{8, 20} represents at least 8 characters and at most 20 characters.
//                $ represents the end of the string.

        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MemberEntity usr = memberService.getMemberEntity(username);
        if (usr != null) {
            return new MyUserDetails(usr);
        } else {
            throw new BadAuthentificationException("Acest utilizator nu a fost gasit");
        }
    }

    public void register(RegisterDTO registerDto, String confirmationGetUrl) {

        if (registerDto.getPassword().equals(registerDto.getConfirmedPassword()) && isValidPassword(registerDto.getPassword())) {
            logger.debug("it's a match");
        } else {
            throw new BadAuthentificationException("Parolele nu sunt identice, te rugam sa incerci din nou");
        }

        String passwordCrypted = BCrypt.hashpw(registerDto.getPassword(), BCrypt.gensalt(12));
        MemberEntity membersEntity = new MemberEntity(String.valueOf(UUID.randomUUID()), registerDto.getUserName(), 0, registerDto.getEmailAddress(), passwordCrypted);
        memberService.saveMember(membersEntity);

        System.out.println("sending " + confirmationGetUrl + "/confirm?memberUUID=" + membersEntity.getMemberUserUuid());
        String myLink = "<a href=\"" + confirmationGetUrl + "/confirm?memberUUID=" + membersEntity.getMemberUserUuid() + "\">Account Activation</a>";
        emailService.sendMail(membersEntity.getEmailAddress(), emailTemplatesService.getById(2).getSubject(), String.format(emailTemplatesService.getById(2).getContentBody(), membersEntity.getUsername(), myLink));

        logger.debug("sending " + confirmationGetUrl + "/confirm?memberUUID=" + memberEntity.getMemberUserUuid());
        
    }

}
