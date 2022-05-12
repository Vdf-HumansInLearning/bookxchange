package com.bookxchange.enums;


public enum UserRoles {
    ADMIN(1, "ADMIN"),
    USER(2, "USER");


    private final Integer code;
    private final String role;

    UserRoles(Integer code, String role) {
        this.code = code;
        this.role = role;
    }
    @Override
    public String toString() {
        return String.valueOf(code);
    }


    public Integer getCode() {
        return code;
    }

    public String getRole() {
        return role;
    }
}
