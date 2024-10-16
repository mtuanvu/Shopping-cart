package com.shoppingCart.Shopping_cart.service.order;

import com.shoppingCart.Shopping_cart.dto.OrderItemDto;
import com.shoppingCart.Shopping_cart.exceptions.ResourceNotFoundException;
import com.shoppingCart.Shopping_cart.model.Order;
import com.shoppingCart.Shopping_cart.model.OrderItem;
import com.shoppingCart.Shopping_cart.repository.OrderItemRepository;
import com.shoppingCart.Shopping_cart.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemService implements IOrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderItemDto addOrderItem(Long orderId, OrderItemDto orderItemDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        OrderItem orderItem = modelMapper.map(orderItemDto, OrderItem.class);
        orderItem.setOrder(order);
        OrderItem savedItem = orderItemRepository.save(orderItem);
        return modelMapper.map(savedItem, OrderItemDto.class);
    }

    @Override
    public List<OrderItemDto> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderItem -> modelMapper.map(orderItem, OrderItemDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrderItem(Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order Item not found"));

        if (!orderItem.getOrder().getId().equals(orderId)) {
            throw new ResourceNotFoundException("Order ID does not match with OrderItem's Order");
        }

        orderItemRepository.delete(orderItem);
    }
}
