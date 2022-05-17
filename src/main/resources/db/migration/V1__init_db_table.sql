BEGIN;
CREATE DATABASE IF NOT EXISTS bookOLX ;

USE bookOLX;


CREATE TABLE authors (
                         author_id int auto_increment,
                         authors_uuid varchar(36) NOT NULL unique,
                         name VARCHAR(25),
                         surname VARCHAR (25),
                         PRIMARY KEY (author_id)
);



CREATE TABLE books (
                       book_id int auto_increment not null ,
                       isbn varchar(20) not null unique,
                       title varchar(100),
                       description text,
                       quantity int,
                       primary key (book_id)
);

CREATE TABLE authors_books_mtm (
                                   id int NOT NULL unique auto_increment,
                                   book_isbn varchar(36) NOT NULL,
                                   authors_uuid varchar(36) NOT NULL,
                                   PRIMARY KEY (id)
);

CREATE TABLE roles (
                       role_id int auto_increment not null,
                       role_name varchar (25),
                       primary key (role_id)
);

CREATE TABLE privilages (
                            privilage_id int auto_increment not null ,
                            privilage_name varchar(25),
                            primary key (privilage_id)
);

CREATE TABLE roles_privilages_mtm (
                                      id int auto_increment not null,
                                      role_id int,
                                      privilage_id int,
                                      primary key (id),
                                      FOREIGN KEY (role_id) REFERENCES roles(role_id),
                                      FOREIGN KEY (privilage_id) REFERENCES privilages(privilage_id)
);


CREATE TABLE members (
                         member_user_id int auto_increment not null ,
                         member_uuid varchar(36) NOT NULL unique,
                         username varchar(50) UNIQUE,
                         password TEXT(50),
                         points INT,
                         email_address varchar(100) UNIQUE,
                         role_id INT,
                         is_email_confirmed boolean default 0,
                         primary key (member_user_id)
);
CREATE TABLE user_roles_mtm (

    id int auto_increment not null,
    member_uuid varchar(36) NOT NULL,
    role_id int,
                            primary key (id)
);

CREATE TABLE book_market (
                            book_market_id int auto_increment not null ,
                            book_market_uuid VARCHAR(36) not null unique,
                            user_uuid VARCHAR(36) not null,
                            book_isbn VARCHAR(36) not null,
                            book_state VARCHAR(50),
                            for_sell boolean,
                            sell_price double,
                            for_rent boolean,
                            rent_price double,
                            book_status VARCHAR(50),
                            primary key(book_market_id),
                            foreign key (user_uuid) references members(member_uuid),
                            foreign key (book_isbn) references books(isbn)
);

CREATE TABLE transaction (
                             id bigint not null unique auto_increment,
                             market_book_uuid_supplier VARCHAR(36) not null  ,
                             market_book_uuid_client VARCHAR(36)   ,
                             member_uuid_from VARCHAR(36) not null  ,
                             member_uuid_to VARCHAR(36) not null,
                             transaction_type VARCHAR(10),
                             transaction_status VARCHAR (20),
                             transaction_date date,
                             expected_return_date date,
                             primary key(id),
                             foreign key (member_uuid_from) references members(member_uuid),
                             foreign key (member_uuid_to) references members(member_uuid),
                             foreign key (market_book_uuid_supplier) references book_market(book_market_uuid)
);


CREATE TABLE rating (
                        rating_id INT not null unique AUTO_INCREMENT,
                        grade INT,
                        description VARCHAR(200),
                        left_by_uuid VARCHAR(36),
                        user_uuid VARCHAR(36),
                        book_isbn varchar(36),
                        primary key (rating_id)
);

CREATE TABLE emails (
                        id int AUTO_INCREMENT,
                        content varchar(150),
                        sent_date DATE,
                        status varchar(20),
                        member_id int NOT NULL,
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
                               market_book_uuid varchar(36) not null,
                               email_template_id int,
                               sent tinyint default 0,
                               member_uuid varchar(36) not null,
                               primary key (id),
                               foreign key (email_template_id) references email_templates(id),
                               foreign key (market_book_uuid) references book_market(book_market_uuid),
                               foreign key (member_uuid) references members(member_uuid)
);



ALTER TABLE notifications ADD CONSTRAINT
    UNIQUE_Table UNIQUE
        (
         market_book_uuid,
         member_uuid
            );