package com.natal.salesorderservice.controller.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;

@Data
public class CreateOrderTO {

    @JsonProperty
    @CPF
    private String clientDocument;

    @JsonProperty
    @NotBlank
    private String productCode;
}
