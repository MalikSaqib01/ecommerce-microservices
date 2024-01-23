package com.example.orderservice.dto;


import lombok.*;

@Data
@Builder
public class InventoryResponse {

    private String skuCode;
    private boolean isInStock;
}
