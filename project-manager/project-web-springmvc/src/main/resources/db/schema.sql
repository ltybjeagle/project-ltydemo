drop table if exists t_causer;
create table t_causer (
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
createTime date ,
modifyTime date ,
version int
);