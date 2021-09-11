package com.univesp.pi6backend.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.univesp.pi6backend.repository.ProductEnum;
import lombok.Data;

import javax.persistence.Column;

@Data
public class ProductDTO {

    @JsonProperty
    Long id;

    @JsonProperty
    ProductEnum product;

    @Column
    Double price;

    @Column
    String seller;

}
