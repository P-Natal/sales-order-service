create table sales_order
(
    id              bigserial constraint order_key primary key,
    external_id     varchar(100) not null,
    client_document varchar(20) not null,
    product_code    varchar(50) not null,
    price           float NOT NULL,
    status          varchar(20) not null,
    registry_date   timestamp not null,
    last_update     timestamp not null
);