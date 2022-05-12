package com.bookxchange.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Objects;

@Entity
@Table(name = "members", uniqueConstraints = {@UniqueConstraint(columnNames = "username"),
                                              @UniqueConstraint(columnNames = "email_address")})
@Getter
@Setter
@ToString
public class MembersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "member_user_id")
    private Integer memberUserId;
    @Column(name = "member_uuid")
    private String memberUserUuid;
    @Basic
    @Column(name = "username", unique = true)
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "points")
    private Integer points;
    @Basic
    @Email
    @Column(name = "email_address", unique = true)
    private String emailAddress;
    @Column(name = "is_email_confirmed")
    private byte isEmailConfirmed;

    @ManyToOne
    @JoinColumn(name="role_id", nullable=false)
    private RolesEntity role;



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
