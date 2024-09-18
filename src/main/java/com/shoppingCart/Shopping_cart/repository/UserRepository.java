package com.shoppingCart.Shopping_cart.repository;

import com.shoppingCart.Shopping_cart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
