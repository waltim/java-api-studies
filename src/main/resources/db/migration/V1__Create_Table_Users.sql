-- auto-generated definition
create table users
(
    id         bigint auto_increment
        primary key,
    city       varchar(255) null,
    complement varchar(255) null,
    country    varchar(255) null,
    email      varchar(255) null,
    name       varchar(255) null,
    number     varchar(255) null,
    password   varchar(255) null,
    state      varchar(255) null,
    street     varchar(255) null,
    constraint UK6dotkott2kjsp8vw4d0m25fb7
        unique (email)
);