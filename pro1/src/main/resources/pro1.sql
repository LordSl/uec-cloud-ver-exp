drop database if exists `uec`;
create database `uec` default charset `utf8`;
use `uec`;

create table if not exists `user`(
    username varchar(32) not null,
    pwd varchar(32) not null,
    primary key (username)
)engine = `innodb`;

insert into user values('obama','123456');