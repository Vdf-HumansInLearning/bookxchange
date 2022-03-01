package com.bookxchange.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "Notifications")
@Entity
@Data
@ToString
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    int id;
    @Column(name = "MARKETBOOKID")
    String bookId;
    @Column(name = "type")
    String type;
    @Column(name = "SENT")
    int sent;
    @Column(name = "MEMBERID")
    String memberId;


    
}
