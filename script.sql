
CREATE SCHEMA payment
    AUTHORIZATION fbecgodrwcprmb;
COMMENT ON SCHEMA payment
    IS 'standard public schema';

GRANT ALL ON SCHEMA payment TO PUBLIC;

GRANT ALL ON SCHEMA payment TO fbecgodrwcprmb;

set schema 'payment';


create table user_role
(
    id   serial
        primary key,
    role varchar(50) not null
        unique
);

alter table user_role
    owner to fbecgodrwcprmb;

create table user_entity
(
    id       serial
        primary key,
    name     varchar     not null,
    surname  varchar     not null,
    email    varchar     not null
        unique,
    password varchar     not null,
    role     varchar(50) not null
        constraint fk_role
            references user_role (role),
    status   varchar
);

alter table user_entity
    owner to fbecgodrwcprmb;

create table card_type
(
    id   serial,
    type varchar(100) not null
        unique
);

alter table card_type
    owner to fbecgodrwcprmb;

create table language
(
    id         serial
        primary key,
    short_name varchar(10) not null
        unique,
    full_name  varchar     not null
);

alter table language
    owner to fbecgodrwcprmb;

create table status
(
    id          serial
        primary key,
    name        varchar not null
        unique,
    language_id integer not null
        constraint fk_language
            references language
);

alter table status
    owner to fbecgodrwcprmb;

create table currency
(
    id   serial
        primary key,
    name varchar not null
        unique
);

alter table currency
    owner to fbecgodrwcprmb;

create table account
(
    id             serial
        primary key,
    account_number bigint  not null
        unique,
    user_login     varchar not null
        constraint fk_user_login
            references user_entity (email),
    amount         numeric not null,
    currency_name  varchar not null
        constraint fk_currency_name
            references currency (name),
    status         varchar not null
        constraint fk_status
            references status (name)
);

alter table account
    owner to fbecgodrwcprmb;

create table card
(
    id          serial
        primary key,
    card_number bigint       not null
        unique,
    pin_num     integer      not null,
    cvv_num     integer      not null,
    expiry_date varchar      not null,
    card_type   varchar(100) not null
        constraint fk_card_type
            references card_type (type),
    account_num bigint       not null
        constraint fk_account_num
            references account (account_number)
);

alter table card
    owner to fbecgodrwcprmb;

create table vault
(
    number serial
        primary key,
    acc    bigint
);

alter table vault
    owner to fbecgodrwcprmb;

create table payment_status
(
    id          serial
        primary key,
    status      varchar not null
        unique,
    language_id bigint  not null
);

alter table payment_status
    owner to fbecgodrwcprmb;

create table payment
(
    id                       serial
        primary key,
    payment_number           bigint  not null
        unique,
    payment_from_account_num bigint  not null
        constraint fk_from_acc_num
            references account (account_number),
    payment_to_account_num   bigint  not null
        constraint fk_to_acc_num
            references account (account_number),
    time                     varchar not null,
    amount                   numeric not null,
    payment_status           varchar not null
        constraint fk_payment_status
            references payment_status (status),
    sender                   varchar not null,
    recipient                varchar
);

alter table payment
    owner to fbecgodrwcprmb;

insert into payment.user_role (id, role) values (1, 'ADMIN');
insert into payment.user_role (id, role) values (2, 'USER');

insert into payment.user_entity (id, name, surname, email, password, role, status) values (4, 'blocked user', 'blocked', 'b@b.b', '12345', 'USER', 'ACTIVE');
insert into payment.user_entity (id, name, surname, email, password, role, status) values (3, 'qwdqwn', 'oajnvconv', 'q@q.q', '12345', 'USER', 'ACTIVE');
insert into payment.user_entity (id, name, surname, email, password, role, status) values (2, 'svdbhsdvbs', 'jknvsklnd', 'c@c.c', 'qazwsx', 'USER', 'ACTIVE');
insert into payment.user_entity (id, name, surname, email, password, role, status) values (1, 'adm', 'in', 'admin@admin.admin', 'admin', 'ADMIN', 'ACTIVE');
insert into payment.user_entity (id, name, surname, email, password, role, status) values (7, 'new', 'new', 'new@new.new', 'new', 'USER', 'BLOCKED');

insert into payment.card_type (id, type) values (1, 'VISA');
insert into payment.card_type (id, type) values (2, 'MASTERCARD');

insert into payment.language (id, short_name, full_name) values (1, 'uk', 'Ukraine');
insert into payment.language (id, short_name, full_name) values (2, 'en', 'English');

insert into payment.status (id, name, language_id) values (2, 'PENDING', 2);
insert into payment.status (id, name, language_id) values (3, 'ACTIVE', 2);
insert into payment.status (id, name, language_id) values (4, 'BLOCKED', 2);
insert into payment.status (id, name, language_id) values (5, 'АКТИВНИЙ', 1);
insert into payment.status (id, name, language_id) values (6, 'АКТИВУЄТЬСЯ', 1);
insert into payment.status (id, name, language_id) values (7, 'ЗАБЛОКОВАНИЙ', 1);


insert into payment.payment_status (id, status, language_id) values (1, 'PREPARED', 2);
insert into payment.payment_status (id, status, language_id) values (3, 'DELETED', 2);
insert into payment.payment_status (id, status, language_id) values (4, 'ПІДГОТОВЛЕНИЙ', 1);
insert into payment.payment_status (id, status, language_id) values (5, 'ВІДПРВАЛЕНИЙ', 1);
insert into payment.payment_status (id, status, language_id) values (7, 'ВИДАЛЕНИЙ', 1);
insert into payment.payment_status (id, status, language_id) values (8, 'BLOCKED', 2);
insert into payment.payment_status (id, status, language_id) values (9, 'PENDING', 2);
insert into payment.payment_status (id, status, language_id) values (10, 'APPROVED', 2);
insert into payment.payment_status (id, status, language_id) values (11, 'ОБРОБЛЮЄТЬСЯ', 1);
insert into payment.payment_status (id, status, language_id) values (12, 'ЗАБЛОКОВАНИЙ', 1);
insert into payment.payment_status (id, status, language_id) values (13, 'ПІДТВЕРДЖЕНИЙ', 1);
insert into payment.payment_status (id, status, language_id) values (2, 'SEND', 2);



