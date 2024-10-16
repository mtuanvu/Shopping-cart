package com.shoppingCart.Shopping_cart.service.order;

import com.shoppingCart.Shopping_cart.dto.OrderItemDto;

import java.util.List;
import java.util.Optional;

public interface IOrderItemService {

    OrderItemDto addOrderItem(Long orderId, OrderItemDto orderItemDto);

    void deleteOrderItem(Long orderItemId, Long itemId);

    List<OrderItemDto> getOrderItemsByOrderId(Long orderId);
}
