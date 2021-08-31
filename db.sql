create table user_role
(
    id   serial primary key not null,
    role varchar(50)        not null,
    unique (role)
);


create table user_entity
(
    id       serial      not null,
    login    varchar     not null,
    password varchar     not null,
    role     varchar(50) not null,
    primary key (id),
    constraint fk_role foreign key (role) references user_role (role),
    unique (login)
);

create table card_type
(
    id   serial       not null,
    type varchar(100) not null,
    unique (type)
);

create table card
(
    id             serial primary key not null,
    card_number    bigint             not null,
    pin_num        int                not null,
    cvv_num        int                not null,
    expiry_date    date               not null,
    card_type      varchar(100)       not null,
    constraint fk_card_type foreign key (card_type) references card_type (type),
    unique (card_number)
);

create table currency
(
    id   serial primary key not null,
    name varchar            not null,
    unique (name)
);

create table status
(
    id   serial primary key not null,
    name varchar            not null,
    unique (name)
);

create table account
(
    id             serial primary key not null,
    card_holder_id int                not null,
    card_id        int                not null,
    amount         decimal            not null,
    currency_name  varchar            not null,
    status         varchar            not null,
    constraint fk_card_holder foreign key (card_holder_id) references user_entity (id),
    constraint fk_card_id foreign key (card_id) references card (id),
    constraint fk_currency_name foreign key (currency_name) references currency (name),
    constraint fk_status foreign key (status) references status (name),
    unique (id)
);

create table payment_status
(
    id     serial primary key not null,
    status varchar            not null,
    unique (status)
);

create table payment
(
    id                      serial primary key not null,
    payment_from_account_id int                not null,
    payment_to_account_id   int                not null,
    time                    timestamp          not null,
    amount                  decimal            not null,
    payment_status          varchar            not null,
    constraint fk_from_acc_id foreign key (payment_from_account_id) references account (id),
    constraint fk_to_acc_id foreign key (payment_to_account_id) references account (id),
    constraint fk_payment_status foreign key (payment_status) references payment_status (status)
);



