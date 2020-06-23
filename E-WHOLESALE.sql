-- TABLE FOR CUSTOMER

create table customer(
customer_id VARCHAR(50) PRIMARY KEY NOT NULL,
first_name VARCHAR(40),
last_name VARCHAR(40),
email VARCHAR(60) NOT NULL,
age INT NOT NULL,
gender VARCHAR(20),
contact VARCHAR(20),
address VARCHAR(100)
);

-- TABLE FOR PRODUCT
create table product(
product_id VARCHAR(40) PRIMARY KEY NOT NULL,
product_name VARCHAR(50) NOT NULL,
price INT NOT NULL,
product_description VARCHAR(100)
);

