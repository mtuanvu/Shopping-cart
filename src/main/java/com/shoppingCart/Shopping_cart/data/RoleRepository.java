package com.shoppingCart.Shopping_cart.data;

import com.shoppingCart.Shopping_cart.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RoleRepository extends JpaRepository<Role, Long > {
    Collection<Object> findByName(String role);
}
