package com.bookxchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles_mtm", joinColumns = {
            @JoinColumn(name = "member_uuid") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<RolesEntity> roles;

    public MembersEntity() {
    }

//    public MembersEntity(String memberUserUuid, String username, Integer points, String emailAddress, String password) {
//        this.memberUserUuid = memberUserUuid;
//        this.username = username;
//        this.points = points;
//        this.emailAddress = emailAddress;
//        this.password = password;
//    }


    public MembersEntity(String memberUserUuid, String username, Integer points, String emailAddress, String password) {
        this.memberUserUuid = memberUserUuid;
        this.username = username;
        this.password = password;
        this.points = points;
        this.emailAddress = emailAddress;
        this.roles = new HashSet<>();
    }
}
