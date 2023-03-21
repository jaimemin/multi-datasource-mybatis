package com.tistory.jaimemin.multidatasourcemybatis.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

    private Integer id;

    private String productName;

    private Integer quantity;

    private String createdBy;
}
