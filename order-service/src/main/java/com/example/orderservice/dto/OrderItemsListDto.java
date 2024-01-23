package com.example.orderservice.dto;

import lombok.*;

import java.math.BigDecimal;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsListDto {
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;

}
