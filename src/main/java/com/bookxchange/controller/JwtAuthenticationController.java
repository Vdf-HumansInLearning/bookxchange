package com.bookxchange.controller;

import com.bookxchange.dto.RegisterDTO;
import com.bookxchange.exception.BadAuthentificationException;
import com.bookxchange.model.MemberEntity;
import com.bookxchange.security.JwtRequest;
import com.bookxchange.security.JwtResponse;
import com.bookxchange.security.JwtTokenUtil;
import com.bookxchange.security.JwtUserDetailsService;
import com.bookxchange.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin
public class JwtAuthenticationController {


    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final MemberService memberService;

    @Autowired
    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService, MemberService memberService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.memberService = memberService;
    }


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping(value = "/register")
    public void register(@RequestBody RegisterDTO registerDto, HttpServletRequest request) {

        userDetailsService.register(registerDto, request.getRequestURL().toString());
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new BadAuthentificationException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadAuthentificationException("INVALID_CREDENTIALS", e);
        }
    }

    @GetMapping(value = "/register/confirm", produces = "text/html")
    public String confirmEmailAddress(@RequestParam String memberUUID) {
        MemberEntity confirmedMember = memberService.findByUuid(memberUUID);
        String name = confirmedMember.getUsername();
        confirmedMember.setIsEmailConfirmed((byte) 1);
        memberService.saveMember(confirmedMember);

        return "<html>\n" +
                "  <head>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Nunito+Sans:400,400i,700,900&display=swap\" rel=\"stylesheet\">\n" +
                "  </head>\n" +
                "    <style>\n" +
                "      body {\n" +
                "        text-align: center;\n" +
                "        padding: 40px 0;\n" +
                "        background: #EBF0F5;\n" +
                "      }\n" +
                "        h1 {\n" +
                "          color: #88B04B;\n" +
                "          font-family: \"Nunito Sans\", \"Helvetica Neue\", sans-serif;\n" +
                "          font-weight: 900;\n" +
                "          font-size: 40px;\n" +
                "          margin-bottom: 10px;\n" +
                "        }\n" +
                "        p {\n" +
                "          color: #404F5E;\n" +
                "          font-family: \"Nunito Sans\", \"Helvetica Neue\", sans-serif;\n" +
                "          font-size:20px;\n" +
                "          margin: 0;\n" +
                "        }\n" +
                "      i {\n" +
                "        color: #9ABC66;\n" +
                "        font-size: 100px;\n" +
                "        line-height: 200px;\n" +
                "        margin-left:-15px;\n" +
                "      }\n" +
                "      .card {\n" +
                "        background: white;\n" +
                "        padding: 60px;\n" +
                "        border-radius: 4px;\n" +
                "        box-shadow: 0 2px 3px #C8D0D8;\n" +
                "        display: inline-block;\n" +
                "        margin: 0 auto;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <body>\n" +
                "      <div class=\"card\">\n" +
                "      <div style=\"border-radius:200px; height:200px; width:200px; background: #F8FAF5; margin:0 auto;\">\n" +
                "        <i class=\"checkmark\">✓</i>\n" +
                "      </div>\n" +
                "        <h1>Success</h1> \n" +
                "        <p>Thank you, " + name + ", for confirming your e-mail address;<br/> you may now close this page!</p>\n" +
                "      </div>\n" +
                "    </body>\n" +
                "</html>";
    }
}
