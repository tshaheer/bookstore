-- All DDL script files are combined here, so only this script needs to be executed

BEGIN;
CREATE DATABASE IF NOT EXISTS BOOKSTORE;
USE BOOKSTORE;
\. init_user.sql
\. init_products.sql
\. init_orders.sql
\. init_data.sql
commit;