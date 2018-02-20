# Schema creation for user-related tables

drop table if exists USER;
create table USER (
       ID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
       EMAIL_ADDRESS char(50) not null unique,
       PASSWORD char(20) not null,
       primary key(ID),
       UNIQUE (EMAIL_ADDRESS));
	   
drop table if exists USER_ROLE;
create table USER_ROLE (
       USER_KEY BIGINT references USER,
       ROLE_NAME char(30) not null,
       primary key(USER_KEY, ROLE_NAME));

drop table if exists ADDRESS;
create table ADDRESS (
       ID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
       FIRST_NAME char(30) not null,
       LAST_NAME char(40) not null,
       STREET_1 char(128) not null,
       STREET_2 char(128),
       CITY char(50) not null,
       STATE char(50) not null,
       POSTAL_CODE char(10) not null,
       primary key(ID));

drop table if exists ADDRESS_BOOK;
create table ADDRESS_BOOK (
       USER_KEY BIGINT references USER,
       ADDRESS_KEY BIGINT references ADDRESS);

drop table if exists CREDIT_CARD;
create table CREDIT_CARD (
	   ID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
       CARD_TYPE char(20) not null,
       CARD_NUMBER char(25) not null,
       CARD_OWNERNAME char(50) not null,
       CARD_EXPMONTH int not null,
       CARD_EXPYEAR int not null,
       USER_KEY BIGINT references USER,
       primary key(ID));

# User role Data population
delete from USER_ROLE;
delete from USER;

insert into USER (EMAIL_ADDRESS, PASSWORD) values ('admin@admin.com', 'admin');
insert into USER (EMAIL_ADDRESS, PASSWORD) values ('customer@customer.com', 'customer');

insert into USER_ROLE (USER_KEY, ROLE_NAME) values ((select id from USER where EMAIL_ADDRESS='admin@admin.com'), 'ROLE_ADMIN');
insert into USER_ROLE (USER_KEY, ROLE_NAME) values ((select id from USER where EMAIL_ADDRESS='customer@customer.com'), 'ROLE_CUSTOMER');