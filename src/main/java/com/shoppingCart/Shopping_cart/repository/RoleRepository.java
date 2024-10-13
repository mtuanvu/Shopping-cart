package com.shoppingCart.Shopping_cart.repository;

import com.shoppingCart.Shopping_cart.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long > {
    Optional<Role> findByName(String role);
}
