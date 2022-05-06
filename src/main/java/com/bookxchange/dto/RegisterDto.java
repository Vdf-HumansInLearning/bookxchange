package com.bookxchange.dto;
import lombok.Data;

@Data
public class RegisterDto {

    private String userName;
    private String emailAddress;
    private String password;
    private String confirmedPassword;
    private String role;

}
