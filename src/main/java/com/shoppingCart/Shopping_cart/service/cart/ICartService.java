package com.shoppingCart.Shopping_cart.service.cart;

import com.shoppingCart.Shopping_cart.model.Cart;
import com.shoppingCart.Shopping_cart.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
