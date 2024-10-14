package com.shoppingCart.Shopping_cart.service.order;

import com.shoppingCart.Shopping_cart.dto.OrderItemDto;
import com.shoppingCart.Shopping_cart.model.OrderItem;
import com.shoppingCart.Shopping_cart.repository.OrderItemRepository;
import com.shoppingCart.Shopping_cart.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemService implements IOrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderItem addOrderItem(OrderItemDto orderItemDto) {
        OrderItem orderItem = modelMapper.map(orderItemDto, OrderItem.class);
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }

    @Override
    public List<OrderItemDto> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findAll()
                .stream()
                .filter(orderItem -> orderItem.getOrder().getId().equals(orderId))
                .map(this::convertToOrderItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderItem> getOrderItemById(Long id){
        return orderItemRepository.findById(id);
    }

    @Override
    public OrderItemDto convertToOrderItemDto(OrderItem orderItem) {
        return modelMapper.map(orderItem, OrderItemDto.class);
    }
}
