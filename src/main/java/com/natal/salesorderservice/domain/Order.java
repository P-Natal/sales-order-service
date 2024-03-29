package com.natal.salesorderservice.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Order {

    private String externalId;

    private String clientDocument;

    private String productCode;

    private String status;

    private Date registryDate;

    private Date lastUpdate;
}
