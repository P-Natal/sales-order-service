package com.natal.salesorderservice.amqp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class SalesOrderEvent {
    @JsonProperty
    private String externalId;

    @JsonProperty
    private String clientDocument;

    @JsonProperty
    private String productCode;

    @JsonProperty
    private String status;

    @JsonProperty
    private Date registryDate;

    @JsonProperty
    private Date lastUpdate;

    public SalesOrderEvent(String externalId, String clientDocument, String productCode, String status, Date registryDate, Date lastUpdate) {
        this.externalId = externalId;
        this.clientDocument = clientDocument;
        this.productCode = productCode;
        this.status = status;
        this.registryDate = registryDate;
        this.lastUpdate = lastUpdate;
    }
}
