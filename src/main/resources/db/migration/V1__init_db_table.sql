BEGIN;
CREATE DATABASE IF NOT EXISTS bookOLX ;

USE bookOLX;


CREATE TABLE Authors (
                         id varchar(36) NOT NULL unique,
                         name VARCHAR(25),
                         surname VARCHAR (25),
                         birthDate DATE,
                         PRIMARY KEY (id)
);


CREATE TABLE books (
                       isbn varchar(20) NOT NULL UNIQUE,
                       title VARCHAR(100),
                       author varchar(36),
                       description TEXT,
                       quantity INT,
                       PRIMARY KEY (isbn),
                       FOREIGN KEY (author) REFERENCES Authors(id)
);



CREATE TABLE Members (
                         userID varchar(36) NOT NULL,
                         username TEXT,
                         primary key (userID)
);


CREATE TABLE BookMarket (
                            id VARCHAR(36) not null unique,
                            userID VARCHAR(36) not null,
                            bookID VARCHAR(36) not null,
                            bookState TEXT,
                            forSell boolean,
                            sellPrice double,
                            forRent boolean,
                            rentPrice double,
                            bookStatus text,
                            primary key(id),
                            foreign key (userID) references Members(userID),
                            foreign key (bookID) references books(isbn)
);


CREATE TABLE Rating (
                        ratingId INT not null unique AUTO_INCREMENT,
                        grade INT,
                        description VARCHAR(200),
                        leftBy VARCHAR(36),
                        userID VARCHAR(36),
                        bookID varchar(20),
                        primary key (ratingId),
                        foreign key (userID) references Members(userID),
                        foreign key (bookID) references books(isbn)
);

CREATE TABLE Emails (
                        ID int auto_increment,
                        emailAddress varchar(50),
                        content varchar(150),
                        whensent DATE,
                        status varchar(20),
                        memberID varchar(36) NOT NULL,
                        primary key(ID),
                        foreign key (memberID) references Members(userID)
);
CREATE TABLE Notifications (
                               ID int auto_increment,
                               emailID int,
                               bookID varchar(36) not null,
                               type varchar(20),    primary key (ID),
                               foreign key (bookID) references BookMarket(id),
                               foreign key (emailID) references Emails(ID)
);

CREATE TABLE EmailTemplates (
                                ID int auto_increment,
                                templateName varchar (50),
                                subject varchar (100),
                                contentBody varchar (300)
);


