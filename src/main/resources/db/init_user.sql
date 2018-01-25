# Schema creation for user-related tables

drop table if exists USER;
create table USER (
       USER_ID int not null auto_increment,
       EMAIL_ADDRESS char(50) not null unique,
       PASSWORD char(20) not null,
       primary key(USER_ID),
       index(EMAIL_ADDRESS));
	   
drop table if exists USER_ROLE;
create table USER_ROLE (
       USER_KEY int references USER,
       ROLE_NAME char(30) not null,
       primary key(USER_KEY, ROLE_NAME));

drop table if exists ADDRESS;
create table ADDRESS (
       ADDRESS_ID int not null auto_increment,
       FIRST_NAME char(30) not null,
       LAST_NAME char(40) not null,
       STREET_1 char(128) not null,
       STREET_2 char(128),
       CITY char(50) not null,
       STATE char(2) not null,
       POSTAL_CODE char(10) not null,
       primary key(ADDRESS_ID));

drop table if exists ADDRESS_BOOK;
create table ADDRESS_BOOK (
       USER_KEY int references USER,
       ADDRESS_KEY int references ADDRESS);

drop table if exists CREDIT_CARD;
create table CREDIT_CARD (
       USER_KEY int references USER,
       CARD_TYPE char(5) not null,
       CARD_NUMBER char(25) not null,
       CARD_OWNERNAME char(50) not null,
       CARD_EXPMONTH int not null,
       CARD_EXPYEAR int not null,
       ADDRESS_KEY int references ADDRESS);

