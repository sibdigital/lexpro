create table if not exists cls_privilege
(
    id            serial       not null
        constraint cls_privilege_pk
            primary key,
    name          varchar(100) not null
);

create table if not exists reg_role_privilege
(
    id      serial  not null
        constraint dep_user_privilege_pk
            primary key,
    id_role integer not null
        constraint fk_role
            references cls_role,
    id_privilege integer not null
        constraint fk_privilege
            references cls_privilege
);

