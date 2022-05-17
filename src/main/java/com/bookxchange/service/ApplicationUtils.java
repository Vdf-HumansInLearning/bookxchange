package com.bookxchange.service;

import com.bookxchange.security.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUtils {

    private static JwtTokenUtil jwtTokenUtil;

    @Autowired
    public ApplicationUtils(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }


    public static String getUserFromToken(String token) {
        Claims claims = jwtTokenUtil.getAllClaimsFromToken(token.substring(7));
        return claims.get("userUUID").toString();

    }


}
