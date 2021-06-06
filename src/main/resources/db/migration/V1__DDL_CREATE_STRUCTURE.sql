create table "public"."order"
(
    id              bigserial constraint firstkey primary key,
    external_id     varchar(50) not null,
    client_document varchar(20) not null,
    product_code    varchar(50) not null,
    status          varchar(20) not null,
    registry_date   timestamp not null,
    last_update     timestamp not null
);