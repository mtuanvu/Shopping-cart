package com.shoppingCart.Shopping_cart.repository;

import com.shoppingCart.Shopping_cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
