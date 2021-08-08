package com.natal.salesorderservice.controller.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateOrderTO {

    @JsonProperty
    private String clientDocument;

    @JsonProperty
    private String productCode;
}
