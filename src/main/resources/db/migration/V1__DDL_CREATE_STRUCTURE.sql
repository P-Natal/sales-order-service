create table order
(
    id              bigserial constraint firstkey primary key,
    client_id       bigint not null,
    product_id      bigint not null,
    status          varchar(50) not null,
    registry_date   date
);