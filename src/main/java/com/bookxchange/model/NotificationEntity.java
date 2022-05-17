package com.bookxchange.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class NotificationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "market_book_uuid")
    private String marketBookUuid;
    @Basic
    @Column(name = "email_template_id")
    private Integer templateType;
    @Basic
    @Column(name = "sent")
    private Byte sent;
    @Basic
    @Column(name = "member_uuid")
    private String memberUuid;


    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
