package com.univesp.pi6backend.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.univesp.pi6backend.repository.ProductEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    @JsonProperty
    Long id;

    @JsonProperty
    ProductEnum product;

    @JsonProperty
    BigDecimal price;

    @JsonProperty
    Integer among;

    @JsonProperty
    String seller;

}
