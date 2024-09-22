package com.shoppingCart.Shopping_cart.service.order;

import com.shoppingCart.Shopping_cart.dto.OrderDto;
import com.shoppingCart.Shopping_cart.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
