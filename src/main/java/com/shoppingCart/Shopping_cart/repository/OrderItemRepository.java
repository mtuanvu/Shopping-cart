package com.shoppingCart.Shopping_cart.repository;

import com.shoppingCart.Shopping_cart.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    void delete(Long orderItemId);
}
