package com.shoppingCart.Shopping_cart.controller;

import com.shoppingCart.Shopping_cart.dto.OrderItemDto;
import com.shoppingCart.Shopping_cart.response.ApiResponse;
import com.shoppingCart.Shopping_cart.service.order.IOrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/orders/item")
@RequiredArgsConstructor
public class OrderItemController {
    private final IOrderItemService orderItemService;

    @PostMapping("/addOrderItem/{orderId}")
    public ResponseEntity<ApiResponse> addOrderItem(
            @PathVariable Long orderId,
            @RequestBody OrderItemDto orderItemDto) {
        try {
            OrderItemDto responseDto = orderItemService.addOrderItem(orderId, orderItemDto);
            return ResponseEntity.ok(new ApiResponse("Order Item Created Successfully!", responseDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error Occurred!", e.getMessage()));
        }
    }

    @GetMapping("/getItem/by/{orderId}")
    public ResponseEntity<ApiResponse> getOrderItemsByOrderId(@PathVariable Long orderId) {
        try {
            List<OrderItemDto> orderItems = orderItemService.getOrderItemsByOrderId(orderId);
            return ResponseEntity.ok(new ApiResponse("Order Items Retrieved Successfully!", orderItems));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Order Not Found!", e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{orderId}/{itemId}")
    public ResponseEntity<ApiResponse> deleteOrderItem(
            @PathVariable Long orderId,
            @PathVariable Long itemId) {
        try {
            orderItemService.deleteOrderItem(orderId, itemId);
            return ResponseEntity.ok(new ApiResponse("Order Item Deleted Successfully!", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Order Item Not Found!", e.getMessage()));
        }
    }
}
