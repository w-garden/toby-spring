create table users (
    id varchar(10) primary key
  , name varchar(20) not null
  , password varchar(10) not null
);

use spring;
alter table users add level tinyint;
alter table users add login int;
alter table users add recommend int;
alter table users add email  varchar(30);