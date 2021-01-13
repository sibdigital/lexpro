create table if not exists cls_user
(
    id            serial       not null
        constraint dep_user_pk
            primary key,
    lastname      varchar(100),
    firstname     varchar(100),
    patronymic    varchar(100),
    login         varchar(100) not null,
    password      varchar(100) not null
);

create unique index if not exists fki_dep_user_login
    on cls_user (login);

create table if not exists cls_role
(
    id            serial       not null
        constraint dep_role_pk
            primary key,
    name          varchar(100) not null
);

create table if not exists reg_user_role
(
    id      serial  not null
        constraint dep_user_role_pk
            primary key,
    id_user integer not null
        constraint fk_user
            references cls_user,
    id_role integer not null
        constraint fk_role
            references cls_role
);
