package com.natal.salesorderservice.controller.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateOrderTO {
    @JsonProperty
    private String externalId;

    @JsonProperty
    private String status;
}
