package com.bookxchange.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "notifications", schema = "bookOLX")
@Data
public class NotificationsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "market_book_id")
    private String marketBookId;
    @Basic
    @Column(name = "email_template_id")
    private String templateType;
    @Basic
    @Column(name = "sent")
    private Byte sent;
    @Basic
    @Column(name = "member_id")
    private String memberId;


}
