package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.repository.IntventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final IntventoryRepository intventoryRepository;
    public List<InventoryResponse> isInStock(List<String> skuCode) {

        List<InventoryResponse> inventoryResponses = intventoryRepository.findBySkuCodeIn(skuCode).stream().map(
                inventory -> InventoryResponse.builder()
                        .skuCode(inventory.getSkuCode())
                        .isInStock(inventory.getQuantity()>0)
                        .build()).toList();

        log.info("Received inventory check request for skuCode: {}", inventoryResponses);

        return inventoryResponses;
    }
}
