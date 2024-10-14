package com.shoppingCart.Shopping_cart.controller;

import com.shoppingCart.Shopping_cart.dto.OrderItemDto;
import com.shoppingCart.Shopping_cart.exceptions.ResourceNotFoundException;
import com.shoppingCart.Shopping_cart.model.OrderItem;
import com.shoppingCart.Shopping_cart.response.ApiResponse;
import com.shoppingCart.Shopping_cart.service.order.IOrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/order-items")
@RequiredArgsConstructor
public class OrderItemController {
    private final IOrderItemService orderItemService;

    @PostMapping("/create/order-item")
    public ResponseEntity<ApiResponse> addOrderItem(@RequestBody OrderItemDto orderItemDto) {
        try {
            OrderItem orderItem = orderItemService.addOrderItem(orderItemDto);
            OrderItemDto responseDto = orderItemService.convertToOrderItemDto(orderItem);
            return ResponseEntity.ok(new ApiResponse("Order Item Created Successfully!", responseDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error Occurred!", e.getMessage()));
        }
    }

    @GetMapping("/{orderItemId}")
    public ResponseEntity<ApiResponse> getOrderItemById(@PathVariable Long orderItemId) {
        try {
            Optional<OrderItem> orderItem = orderItemService.getOrderItemById(orderItemId);
            return orderItem.map(value -> ResponseEntity.ok(new ApiResponse("Order Item Retrieved Successfully!", value)))
                    .orElseGet(() -> ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Order Item Not Found!", null)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Order Item Not Found!", e.getMessage()));
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> getOrderItemsByOrderId(@PathVariable Long orderId) {
        try {
            List<OrderItemDto> orderItems = orderItemService.getOrderItemsByOrderId(orderId);
            return ResponseEntity.ok(new ApiResponse("Order Items Retrieved Successfully!", orderItems));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Order Not Found!", e.getMessage()));
        }
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<ApiResponse> deleteOrderItemById(@PathVariable Long orderItemId) {
        try {
            orderItemService.deleteOrderItem(orderItemId);
            return ResponseEntity.ok(new ApiResponse("Order Item Deleted Successfully!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Order Item Not Found!", e.getMessage()));
        }
    }
}
