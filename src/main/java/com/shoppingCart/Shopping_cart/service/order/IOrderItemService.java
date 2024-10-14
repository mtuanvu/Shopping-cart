package com.shoppingCart.Shopping_cart.service.order;

import com.shoppingCart.Shopping_cart.dto.OrderItemDto;
import com.shoppingCart.Shopping_cart.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface IOrderItemService {
    OrderItem addOrderItem(OrderItemDto orderItemDto);

    void deleteOrderItem(Long orderItemId);

    List<OrderItemDto> getOrderItemsByOrderId(Long orderId);

    Optional<OrderItem> getOrderItemById(Long id);

    OrderItemDto convertToOrderItemDto(OrderItem orderItem);
}
