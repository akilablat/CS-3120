drop table if exists faculty;
create table faculty(
id int primary key auto_increment,
name varchar(100),
isChair boolean,
department_id int
);

drop table if exists department;
create table department(
id int primary key auto_increment,
d_name varchar(100)
);

insert into department value(1,'Computer Science');
insert into department value(2,'Electrical and Computer Engineering');
insert into faculty value(1,'Jonh Doe',true,1);