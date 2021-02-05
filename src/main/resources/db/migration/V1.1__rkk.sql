-- #907
CREATE TABLE if not exists cls_npa_type
(
    id   serial      not null
        constraint cls_npa_type_pk
            primary key,
    name text        not null,
    code varchar(16) not null
);

CREATE TABLE if not exists cls_rkk_status
(
    id   serial      not null
        constraint cls_rkk_status_pk
            primary key,
    name text        not null,
    code varchar(16) not null
);

CREATE TABLE if not exists cls_organization
(
    id                      serial not null
        constraint cls_organization_pkey
            primary key,
    name                    text not null,
    status_import           integer default 0,
    time_import             timestamp,
    time_create             timestamp,
    hash_code               text,
    is_activated            boolean default false,
    is_deleted              boolean default false
);

CREATE TABLE if not exists cls_employee
(
    id         serial not null
        constraint cls_employee_pk
            primary key,
    lastname   varchar(100),
    firstname  varchar(100),
    patronymic varchar(100)
);

CREATE TABLE if not exists cls_session
(
    id     serial not null
        constraint cls_session_pk
            primary key,
    number text,
    date   date
);


CREATE TABLE if not exists doc_rkk
(
    id                          serial not null
        constraint doc_rkk_pk
            primary key,
    rkk_number                  text,
    npa_name                    text,
    registration_date           date,
    introduction_date           date,
    id_npa_type                 integer
        constraint fk_npa_type
            references cls_npa_type,
    legislative_basis           text,
    id_law_subject              integer
        constraint fk_law_subject
            references cls_organization,
    id_speaker                  integer
        constraint fk_speaker
            references cls_employee,
    ready_for_session           boolean,
    id_responsible_organization integer
        constraint fk_responsible_organization
            references cls_organization,
    id_responsible_employee     integer
        constraint fk_responsible_employee
            references cls_employee,
    deadline                    date,
    id_session                  integer
        constraint fk_session
            references cls_session,
    included_in_agenda          date,
    agenda_number               text,
    id_status                   integer
        constraint fk_status
            references cls_rkk_status,
    head_signature              date,
    publication_date            date
);

CREATE TABLE if not exists reg_employee_user
(
    id          serial not null
        constraint reg_employee_user_pkey
            primary key,
    id_user     integer
        constraint fk_user
            references cls_user,
    id_employee integer
        constraint fk_employee
            references cls_employee
);

CREATE TABLE if not exists cls_group_attachment
(
    id     serial not null
        constraint cls_group_attachment_pk
            primary key,
    name   text,
    code   varchar(16)
);

CREATE TABLE if not exists cls_type_attachment
(
    id     serial not null
        constraint cls_type_attachment_pk
            primary key,
    name   text,
    code   varchar(16)
);

-- create table if not exists reg_rkk_file
-- (
--     id                 serial   not null
--         constraint reg_doc_rkk_file_pkey
--             primary key,
--     id_rkk             integer
--         constraint fk_doc_rkk
--             references doc_rkk,
--     id_group           integer
--         constraint fk_group_attachment
--             references cls_group_attachment,
--     id_type            integer
--         constraint fk_type_attachment
--             references cls_type_attachment,
--     id_participant     integer
--         constraint fk_participant
--             references cls_organization,
--     number_attachment  text,
--     signing_date       date,
--     page_count         integer,
--     is_deleted         boolean,
--     time_create        timestamp default CURRENT_TIMESTAMP not null,
--     attachment_path    text,
--     file_name          text,
--     original_file_name text,
--     file_extension     varchar(16),
--     hash               text,
--     file_size          integer,
--     id_operator        integer
--         constraint fk_operator
--             references cls_employee
-- );


create table if not exists reg_rkk_file
(
    id                 serial   not null
        constraint reg_rkk_file_pkey
            primary key,
    id_rkk             integer
        constraint fk_doc_rkk
            references doc_rkk,
    page_count         integer,
    is_deleted         boolean,
    time_create        timestamp default CURRENT_TIMESTAMP not null,
    attachment_path    text,
    file_name          text,
    original_file_name text,
    file_extension     varchar(16),
    hash               text,
    file_size          integer,
    id_operator        integer -- кто загрузил файл
        constraint fk_operator
            references cls_employee
);

create table if not exists reg_rkk_attachment
(
    id                 serial   not null
        constraint reg_rkk_attachment_pkey
            primary key,
    id_rkk             integer
        constraint fk_doc_rkk
            references doc_rkk,
    id_group           integer
        constraint fk_group_attachment
            references cls_group_attachment,
    id_type            integer
        constraint fk_type_attachment
            references cls_type_attachment,
    id_participant     integer
        constraint fk_participant
            references cls_organization,
    number_attachment  text,
    signing_date       date,
    is_deleted         boolean,
    id_rkk_file             integer
        constraint  fk_reg_rkk_file
            references reg_rkk_file
);



