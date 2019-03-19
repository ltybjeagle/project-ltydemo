create table t_user (
guid int not null primary key auto_increment,
code varchar(30),
password varchar(50),
name varchar(100),
sex varchar(1),
age number(3),
phone varchar(11),
email varchar(100),
status number(1),
remark varchar(100),
create data,
modify data,
version int
);