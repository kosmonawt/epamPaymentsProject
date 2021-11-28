create table if not exists user_role
(
    id   serial primary key not null,
    role varchar(50)        not null,
    unique (role)
);


create table if not exists language
(
    id         serial primary key not null,
    short_name varchar(10)        not null,
    full_name  varchar            not null,
    unique (short_name)
);

create table if not exists status
(
    id          serial primary key not null,
    name        varchar            not null,
    language_id int                not null,
    unique (name),
    constraint fk_language foreign key (language_id) references language (id)
);

create table if not exists user_entity
(
    id       serial      not null,
    name     varchar     not null,
    surname  varchar     not null,
    email    varchar     not null,
    password varchar     not null,
    role     varchar(50) not null,
    status   varchar     not null,
    primary key (id),
    constraint fk_role foreign key (role) references user_role (role),
    unique (email)
);

create table if not exists card_type
(
    id   serial       not null,
    type varchar(100) not null,
    unique (type)
);

create table if not exists currency
(
    id   serial primary key not null,
    name varchar            not null,
    unique (name)
);

create table if not exists account
(
    id             serial primary key not null,
    account_number bigint             not null,
    user_login     varchar            not null,
    amount         decimal            not null,
    currency_name  varchar            not null,
    status         varchar            not null,
    constraint fk_user_login foreign key (user_login) references user_entity (email),
    constraint fk_currency_name foreign key (currency_name) references currency (name),
    constraint fk_status foreign key (status) references status (name),
    unique (account_number)
);

create table if not exists card
(
    id          serial primary key not null,
    card_number bigint             not null,
    pin_num     int                not null,
    cvv_num     int                not null,
    expiry_date varchar            not null,
    card_type   varchar(100)       not null,
    account_num bigint             not null,
    constraint fk_card_type foreign key (card_type) references card_type (type),
    constraint fk_account_num foreign key (account_num) references account (account_number),
    unique (card_number)
);

create table if not exists payment_status
(
    id          serial primary key not null,
    status      varchar            not null,
    language_id bigint             not null,
    unique (status)
);

create table if not exists payment
(
    id                       serial primary key not null,
    payment_number           bigint unique      not null,
    payment_from_account_num bigint             not null,
    payment_to_account_num   bigint             not null,
    time                     varchar            not null,
    amount                   decimal            not null,
    payment_status           varchar            not null,
    sender                   varchar            not null,
    recipient                varchar,

    constraint fk_from_acc_num foreign key (payment_from_account_num) references account (account_number),
    constraint fk_to_acc_num foreign key (payment_to_account_num) references account (account_number),
    constraint fk_payment_status foreign key (payment_status) references payment_status (status)
);

create table if not exists vault
(
    number serial primary key not null unique,
    acc    bigint
);


insert into user_role
values (default, 'ADMIN');
insert into user_role
values (default, 'USER');

insert into user_entity
values (default, 'adm', 'in', 'admin@admin.admin', 'admin', 'ADMIN');

insert into card_type
values (default, 'VISA');
insert into card_type
values (default, 'MASTERCARD');
insert into currency
values (default, 'UAH');
insert into currency
values (default, 'USD');
insert into currency
values (default, 'EUR');
insert into language
values (default, 'uk', 'Ukraine');
insert into language
values (default, 'en', 'English');

insert into status
values (default, 'PENDING', (select id from language where short_name like 'en'));

insert into status
values (default, 'ACTIVE', (select id from language where short_name like 'en'));
insert into status
values (default, 'BLOCKED', (select id from language where short_name like 'en'));
insert into status
values (default, 'АКТИВНИЙ', (select id from language where short_name like 'uk'));
insert into status
values (default, 'АКТИВУЄТЬСЯ', (select id from language where short_name like 'uk'));
insert into status
values (default, 'ЗАБЛОКОВАНИЙ', (select id from language where short_name like 'uk'));
insert into payment_status
values (default, 'PREPARED', (select id from language where short_name like 'en'));

insert into payment_status
values (default, 'SENT', (select id from language where short_name like 'en'));
insert into payment_status
values (default, 'BLOCKED', (select id from language where short_name like 'en'));


insert into payment_status
values (default, 'PENDING', (select id from language where short_name like 'en'));

insert into payment_status
values (default, 'APPROVED', (select id from language where short_name like 'en'));
insert into payment_status
values (default, 'DELETED', (select id from language where short_name like 'en'));
insert into payment_status
values (default, 'ПІДГОТОВЛЕНИЙ', (select id from language where short_name like 'uk'));
insert into payment_status
values (default, 'ВІДПРВАЛЕНИЙ', (select id from language where short_name like 'uk'));
insert into payment_status
values (default, 'ОБРОБЛЮЄТЬСЯ', (select id from language where short_name like 'uk'));
insert into payment_status
values (default, 'ВИДАЛЕНИЙ', (select id from language where short_name like 'uk'));
insert into payment_status
values (default, 'ЗАБЛОКОВАНИЙ', (select id from language where short_name like 'uk'));
insert into payment_status
values (default, 'ПІДТВЕРДЖЕНИЙ', (select id from language where short_name like 'uk'));

update account
set amount = 65616.23
where account_number = 32;

select *
from account
where user_login like 'c@c.c';

select *
from payment
where sender = 'c@c.c';

alter table user_entity
    add column status varchar;


/*insert into vault
values (default)
RETURNING *;
    insert into vault values (default) returning number;*/
/*select *
from user_entity
where email = 'a@a.a';
select *
from account
where card_holder_id = (select id from user_entity where email = 'a@a.a');*/
/*-- select * from user_entity where login = 'kjabkjsbavd';
insert into user_role
values (default, 'ADMIN');
insert into user_role
values (default, 'USER');
-- insert into user_entity values (default, 'asffaf','asffasfa','asfasfasf@dsvsddsv.com','aasfsaf','admin');

update user_entity
set name     = 'name2',
    surname  ='surname 2',
    email    = 'email2@email2',
    password = 'password',
    role     ='ADMIN'
where id = 3;

delete
from user_entity
where id = 3;*/
