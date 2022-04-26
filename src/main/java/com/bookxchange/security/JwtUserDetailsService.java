package com.bookxchange.security;

import com.bookxchange.customExceptions.InvalidISBNException;
import com.bookxchange.dto.RegisterDto;
import com.bookxchange.model.MembersEntity;
import com.bookxchange.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    MemberService memberService;
    Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);
    @Autowired
    public JwtUserDetailsService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MembersEntity usr = memberService.getMemberEntity(username);
        if (usr != null) {
            return new User(usr.getUsername(), usr.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Nu");
        }
    }


    public void register(RegisterDto registerDto) {

        if (registerDto.getPassword().equals(registerDto.getConfirmedPassword()) && isValidPassword(registerDto.getPassword()))
        {
            logger.debug("it's a match");
        }
            else {
                throw new InvalidISBNException("Parolele nu sunt identice, te rugam sa incerci din nou");
        }

        String passwordCrypted = BCrypt.hashpw(registerDto.getPassword(), BCrypt.gensalt(12));
        MembersEntity membersEntity = new MembersEntity(String.valueOf(UUID.randomUUID()), registerDto.getUserName(), 0, registerDto.getEmailAddress(), passwordCrypted);
        memberService.saveMember(membersEntity);
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

}