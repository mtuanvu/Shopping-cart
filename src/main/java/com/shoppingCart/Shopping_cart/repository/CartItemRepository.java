package com.shoppingCart.Shopping_cart.repository;

import com.shoppingCart.Shopping_cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);
}
