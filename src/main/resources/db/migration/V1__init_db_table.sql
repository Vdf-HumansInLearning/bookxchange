BEGIN;
CREATE DATABASE IF NOT EXISTS bookOLX ;

USE bookOLX;


CREATE TABLE authors (
                         id varchar(36) NOT NULL unique,
                         name VARCHAR(25),
                         surname VARCHAR (25),
                         PRIMARY KEY (id)
);



CREATE TABLE books (
                       isbn varchar(20) not null unique,
                       title varchar(100),
                       author varchar(36),
                       description text,
                       quantity int,
                       primary key (isbn),
                       foreign key (author) references authors(id)
);

CREATE TABLE author_book_mtm (
                             id varchar(36) NOT NULL unique ,
                             book_isbn varchar(13) NOT NULL,
                             author_id varchar (36) NOT NULL,
                             PRIMARY KEY (id),
                             FOREIGN KEY (author_id) REFERENCES authors(id),
                             FOREIGN KEY (book_isbn) REFERENCES books(isbn)
);



CREATE TABLE members (
                         member_user_id varchar(36) NOT NULL,
                         username TEXT,
                         points INT,
                         email_address varchar(100),
                         primary key (member_user_id)
);


CREATE TABLE book_market (
                            book_market_id VARCHAR(36) not null unique,
                            user_id VARCHAR(36) not null,
                            book_id VARCHAR(36) not null,
                            book_state TEXT,
                            for_sell boolean,
                            sell_price double,
                            for_rent boolean,
                            rent_price double,
                            book_status text,
                            primary key(book_market_id),
                            foreign key (user_id) references members(member_user_id),
                            foreign key (book_id) references books(isbn)
);

CREATE TABLE transaction (
                            id bigint not null unique auto_increment,
                            market_book_id VARCHAR(36) not null unique,
                            member_id_from VARCHAR(36) not null,
                            member_id_to VARCHAR(36) not null,
                            transaction_type VARCHAR(10),
                            transaction_date date,
                            expected_return_date date,
                            primary key(id),
                            foreign key (member_id_from) references members(member_user_id),
                            foreign key (market_book_id) references book_market(book_market_id)
);


CREATE TABLE rating (
                        rating_id INT not null unique AUTO_INCREMENT,
                        grade INT,
                        description VARCHAR(200),
                        left_by VARCHAR(36),
                        user_id VARCHAR(36),
                        book_id varchar(36),
                        primary key (rating_id),
                        foreign key (user_id) references members(member_user_id)
);

CREATE TABLE emails (
                        id int AUTO_INCREMENT,
                        content varchar(150),
                        sent_date DATE,
                        status varchar(20),
                        member_id varchar(36) NOT NULL,
                        primary key(id),
                        foreign key (member_id) references members(member_user_id)
);

CREATE TABLE email_templates (
                                 id int not null AUTO_INCREMENT,
                                 template_name varchar (50),
                                 subject varchar (100),
                                 content_body varchar (300),
                                 primary key(id)
);

CREATE TABLE notifications (
                               id int auto_increment,
                               market_book_id varchar(36) not null,
                               email_template_id int,
                               sent tinyint default 0,
                               member_id varchar(36) not null,
                               primary key (id),
                               foreign key (email_template_id) references email_templates(id),
                               foreign key (market_book_id) references book_market(book_market_id),
                               foreign key (member_id) references members(member_user_id)
);



ALTER TABLE notifications ADD CONSTRAINT
    UNIQUE_Table UNIQUE
    (
     market_book_id,
     member_id
        );




