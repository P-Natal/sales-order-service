package com.natal.salesorderservice.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class OrderTO {

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
}
