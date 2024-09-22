package com.shoppingCart.Shopping_cart.dto;

import com.shoppingCart.Shopping_cart.model.CartItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDto {
    private Long cartId;
    private Set<CartItemDto> items;
    private BigDecimal totalAmount;
}
