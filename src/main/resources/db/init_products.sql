# Schema creation for product-related tables

drop table if exists PRODUCT;
create table PRODUCT (
	   ID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
       ISBN char(36) not null,
       TITLE varchar(128) not null,
       PRICE float not null,
       PUB_DATE date not null,
       DESCRIPTION text,
	   IMAGE_URL varchar(255) not null,
       primary key(ID),
	   UNIQUE (ISBN));

drop table if exists AUTHOR;
create table AUTHOR (
       ID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
       NAME char(128) not null,
       primary key(ID),
       UNIQUE (NAME));

drop table if exists PRODUCT_AUTHOR_XREF;
create table PRODUCT_AUTHOR_XREF (
       PRODUCT_ID int references PRODUCT,
       AUTHOR_ID int references AUTHOR);

drop table if exists CATEGORY;
create table CATEGORY (
       ID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
       NAME char(30) not null,
       primary key(ID),
       UNIQUE (NAME));

drop table if exists CATEGORY_PRODUCT_XREF;
create table CATEGORY_PRODUCT_XREF (
       CATEGORY_ID int references CATEGORY,
       PRODUCT_ID int references PRODUCT);
