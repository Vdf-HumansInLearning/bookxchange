package com.bookxchange.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "email_templates", schema = "bookOLX")
@Data
public class EmailTemplatesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "template_name")
    private String templateName;
    @Basic
    @Column(name = "subject")
    private String subject;
    @Basic
    @Column(name = "content_body")
    private String contentBody;


}
