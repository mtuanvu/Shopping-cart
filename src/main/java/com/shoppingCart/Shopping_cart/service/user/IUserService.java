package com.shoppingCart.Shopping_cart.service.user;

import com.shoppingCart.Shopping_cart.dto.UserDto;
import com.shoppingCart.Shopping_cart.model.User;
import com.shoppingCart.Shopping_cart.request.CreateUserRequest;
import com.shoppingCart.Shopping_cart.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToUserDto(User user);
}
