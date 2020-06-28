--TABLE FOR CUSTOMER
 ----------------------------------------------------------------------------------------------------------------------------

create table customer(custid varchar(50) primary key not null,
firstname varchar(50),
lastname varchar(50),
email varchar(100) not null,
address varchar(200),
gender varchar(10),
age int,
contact varchar(20));
desc customer;
 ----------------------------------------------------------------------------------------------------------------------------

-- TABLE FOR USER INFORMATION
 ----------------------------------------------------------------------------------------------------------------------------

create table userdetails1(userid varchar(50) primary key not null, custid varchar(50),
username varchar(100) not null , password varchar(100) not null,
FOREIGN KEY(custid) references customer(custid));
select userid,custid from userdetails1 where username = 'Swati' and password = 'Swati123';
desc userdetails1;

 ----------------------------------------------------------------------------------------------------------------------------

-- TABLE FOR PRODUCT DETAILS
 ----------------------------------------------------------------------------------------------------------------------------

create table product(id varchar(20) primary key not null,
name varchar(50) not null,
price int not null,
description varchar(200));
desc product;

 ----------------------------------------------------------------------------------------------------------------------------

--TABLE FOR ITEM STOCKS
 ----------------------------------------------------------------------------------------------------------------------------

create table stock(stockid varchar(20) primary key,
productid varchar(20), 
quantity number(20) not null ,
FOREIGN key (productid) references product(id));
desc stock;
insert into stock(stockid,quantity) values('st2',10);

----------------------------------------------------------------------------------------------------------------------------

--TABLE FOR ORDERS
 ----------------------------------------------------------------------------------------------------------------------------

create table orders(orderno varchar(20) primary key not null,
custid varchar(20) not null,
orderdate date,
FOREIGN key (custid) references customer(custid));
desc orders;

 ----------------------------------------------------------------------------------------------------------------------------

--TABLE FOR ORDER DETAILS
 ----------------------------------------------------------------------------------------------------------------------------

create table  orderdetails(productid varchar(50),
orderid varchar(20),
quantity number(20),
FOREIGN key (orderid) references orders(orderno),
FOREIGN key (productid) references product(id));
desc orderdetails;

 ----------------------------------------------------------------------------------------------------------------------------

--TABLE FOR DELIVERY DETAILS
 ----------------------------------------------------------------------------------------------------------------------------

create table shipment(id varchar(50) primary key not null,
address varchar(200) not null,
shipdate date,
contact varchar(20) not null,
orderid varchar(20),
FOREIGN key (orderid)REFERENCES orders(orderno));
desc shipment;

 ----------------------------------------------------------------------------------------------------------------------------

--TABLE FOR PAYMENT
 ----------------------------------------------------------------------------------------------------------------------------

create table payment(payno varchar(20) primary key,
type varchar(20),
paydate date,
amount varchar(20) not null,
orderid varchar(20),
FOREIGN key (orderid) REFERENCES orders(orderno));
desc payment;


--delete from product where id = 'prod2';
--insert into product values('prod6','hanky',900,'blue');
--select quantity from stock where stockid='st2';



desc orders;
ALTER TABLE orders
drop column quantity; 
--delete from orders where custid=1;
--delete from stock;

----------------------------------------------------TABLE VIEW------------------------------------------------------
select * from orders;
select * from customer;
select * from userdetails1;
select * from product;
select * from orderdetails;
select * from shipment;
select * from payment;
select * from stock;

--select product.id,product.name,product.price,product.description,stock.quantity from product inner join stock on product.name = stock.stockid
--where product.name='shirt';
--
--select product.price,stock.quantity from product inner join stock on product.name=stock.stockid where product.name='shirt';
--
--delete orders  where custid = 4;


-------------------------------------------------------------STEPS---------------------------------------------------------------
-- STEP 1: FOR VALIDATING THE CUSTOMER INFORMATION
select userdetails1.username, userdetails1.password,
customer.firstname,customer.lastname,customer.email,customer.age,customer.gender,customer.address
from userdetails1
inner join customer on userdetails1.customerid = customer.custid
where custid = 2;
--
--ALTER table payment
--RENAME COLUMN orderid TO ordid;


-- STEP 2: ORDER DEATILS
select orders.orderno,orderdate
from orders where customerid = 1;
--use rs.next and inner loop


--STEP 3: PRODUCT DETAILS
select orderdetails.productid,product.name,product.price,orderdetails.quantity
from orderdetails
inner join product on orderdetails.productid = product.id 
where orderdetails.orderid='order5';


--STEP 4: DELIVERY DETAILS
select payment.amount,payment.payno,payment.type,payment.paydate,
shipment.contact,shipment.address,shipment.shipdate
from shipment inner join payment on payment.ordid = shipment.ordrid
where ordrid='order5';


-- STEP 5: PAYMENT DETAILS
--select payno,type,paydate,amount from payment where ordid = 'order5';

--order details
select orders.orderno,payment.amount,
payment.type,payment.payno, payment.paydate,
shipment.shipdate, shipment.contact
from orders
inner join orderdetails on orders.orderno = orderdetails.orderid
inner join product on orderdetails.productid = product.id
inner join shipment on orderdetails.orderid = shipment.ordrid
inner join payment on shipment.ordrid = payment.ordid
where customerid = 1;

desc orders;
