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
       AUTHOR_ID int not null unique,
       AUTHOR_NAME char(128) not null,
       primary key(AUTHOR_ID));

drop table if exists PRODUCT_AUTHOR_XREF;
create table PRODUCT_AUTHOR_XREF (
       PRODUCT_ISBN char(20) references PRODUCT,
       AUTHOR_ID int references AUTHOR);

drop table if exists CATEGORY;
create table CATEGORY (
       CATEGORY_ID int not null,
       CATEGORY_NAME char(30) not null,
       FEATURED_PRODUCT char(20) references PRODUCT,
       primary key(CATEGORY_ID));

drop table if exists CATEGORY_PRODUCT_XREF;
create table CATEGORY_PRODUCT_XREF (
       CATEGORY_ID int references CATEGORY,
       PRODUCT_ISBN char(20) references PRODUCT);
