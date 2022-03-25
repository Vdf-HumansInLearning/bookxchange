package com.bookxchange.pojo;


import com.bookxchange.enums.EmailTemplateType;

public interface NotificationHelper {
    String getEmail_Address();

    String getTitle();

    EmailTemplateType getTemplate_Name();

    String getSubject();

    String getContent_Body();

    String getUsername();

    Integer getNotid();

    Integer getMember_User_Id();

    String getMember_uuid();
}
