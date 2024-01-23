package com.example.orderservice.service;


import com.example.orderservice.dto.InventoryResponse;
import com.example.orderservice.dto.OrderItemsListDto;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderItemsList;
import com.example.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClient;



    public String createOrder(OrderRequest orderRequest) throws IllegalAccessException {

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        order.setOrderItemsList(orderRequest.getOrderItemsLists().stream()
                .map(orderItemsListDto -> mapToDto(orderItemsListDto)).toList());

       List<String> skuCodes = orderRequest.getOrderItemsLists()
               .stream().map(orderItem -> orderItem.getSkuCode()).toList();

      InventoryResponse[] inventoryResponseArray =  webClient.build().get()
                .uri("http://INVENTORY-SERVICE/api/inventory",
                uriBuilder -> uriBuilder.queryParam("skuCodes" , skuCodes).build())

                        .retrieve()
                        .bodyToMono(InventoryResponse[].class)
                        .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::isInStock);

      if (allProductsInStock) {
          System.out.println(inventoryResponseArray.length);
          orderRepository.save(order);
          return "order placed";
      }
      else {
          throw new IllegalAccessException("product is not in stock...");
      }



    }

    private OrderItemsList mapToDto(OrderItemsListDto orderItemsListDto) {


        return  OrderItemsList.builder()
                .skuCode(orderItemsListDto.getSkuCode())
                .price(orderItemsListDto.getPrice())
                .quantity(orderItemsListDto.getQuantity())
                .build();
    }

}
