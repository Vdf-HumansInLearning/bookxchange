package com.bookxchange.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterDTO {

    private String userName;
    private String emailAddress;
    private String password;
    private String confirmedPassword;
}
