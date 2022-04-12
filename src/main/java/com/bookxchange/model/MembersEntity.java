package com.bookxchange.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "members", schema = "bookOLX")
@Data
public class MembersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "member_user_id")
    private Integer memberUserId;
    @Column(name = "member_uuid")
    private String memberUserUuid;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "points")
    private Integer points;
    @Basic
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "password")
    private String password;


}
