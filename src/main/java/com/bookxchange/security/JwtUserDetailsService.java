package com.bookxchange.security;

import java.util.ArrayList;

import com.bookxchange.model.MembersEntity;
import com.bookxchange.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    MemberService memberService;

    @Autowired
    public JwtUserDetailsService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MembersEntity usr = memberService.getMemberEntity(username);
        if (usr != null) {
            return new User(usr.getUsername(),usr.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Nu");
        }
    }
}
