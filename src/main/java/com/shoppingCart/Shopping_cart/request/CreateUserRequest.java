package com.shoppingCart.Shopping_cart.request;

import lombok.Data;

import java.util.Set;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<String> roles;
}
