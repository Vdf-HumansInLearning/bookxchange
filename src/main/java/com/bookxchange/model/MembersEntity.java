package com.bookxchange.model;

import lombok.AllArgsConstructor;
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
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "points")
    private Integer points;
    @Basic
    @Column(name = "email_address")
    private String emailAddress;



    public MembersEntity() {
    }

    public MembersEntity(String memberUserUuid, String username, Integer points, String emailAddress, String password) {
        this.memberUserUuid = memberUserUuid;
        this.username = username;
        this.points = points;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
