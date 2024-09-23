package com.shoppingCart.Shopping_cart.controller;

import com.shoppingCart.Shopping_cart.model.Cart;
import com.shoppingCart.Shopping_cart.model.User;
import com.shoppingCart.Shopping_cart.response.ApiResponse;
import com.shoppingCart.Shopping_cart.service.cart.ICartItemService;
import com.shoppingCart.Shopping_cart.service.cart.ICartService;
import com.shoppingCart.Shopping_cart.service.user.IUserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;


    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
        try {
            User user = userService.getAuthenticatedUser();
            Cart cart = cartService.initializeNewCart(user);

            cartItemService.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Add item success", null));
        } catch (ResolutionException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (JwtException e) {
            return ResponseEntity.status(UNAUTHORIZED)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,
                                                          @PathVariable Long itemId) {
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new ApiResponse("Remove item success", null));
        } catch (ResolutionException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long itemId,
                                                          @RequestParam Integer quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new ApiResponse("Update success", null));
        } catch (ResolutionException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
}
