package com.natal.salesorderservice.controller.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateOrderTO {

    @JsonProperty
    @NotBlank
    private String status;
}
