# Schema creation for orders-related tables

drop table if exists ORDERS;
create table ORDERS (
       ID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
       EMAIL_ADDRESS char(50),
       ADDRESS_KEY int references ADDRESS,
       CARD_KEY int references CREDIT_CARD,
       ORDER_NUMBER BIGINT,
       ORDER_DATE date,
       ORDER_SUBTOTAL float,
       ORDER_TAX float,
       ORDER_SHIPPING float,
       ORDER_TOTAL float,
       ORDER_STATUS char(10),
       primary key(ID),
       UNIQUE(ORDER_NUMBER));
	   
drop table if exists ORDER_ITEM;
create table ORDER_ITEM (
	   ID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
       ORDER_ID int references ORDERS,
       PRODUCT_ISBN char(20) not null,
       PRODUCT_TITLE varchar(128) not null,
       QUANTITY int not null,
       UNIT_PRICE float not null,
       TOTAL_PRICE float not null,
       primary key(ID));

