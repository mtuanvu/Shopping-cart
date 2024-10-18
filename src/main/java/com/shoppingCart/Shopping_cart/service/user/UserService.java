package com.shoppingCart.Shopping_cart.service.user;

import com.shoppingCart.Shopping_cart.repository.RoleRepository;
import com.shoppingCart.Shopping_cart.dto.UserDto;
import com.shoppingCart.Shopping_cart.exceptions.AlreadyExistsException;
import com.shoppingCart.Shopping_cart.exceptions.ResourceNotFoundException;
import com.shoppingCart.Shopping_cart.model.Role;
import com.shoppingCart.Shopping_cart.model.User;
import com.shoppingCart.Shopping_cart.repository.UserRepository;
import com.shoppingCart.Shopping_cart.request.CreateUserRequest;
import com.shoppingCart.Shopping_cart.request.RegisterUserRequest;
import com.shoppingCart.Shopping_cart.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found!")
        );
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());

                    Role defaultRole = roleRepository.findByName("ROLE_USER")
                            .orElseThrow(() -> new RuntimeException("Role not found"));
                    user.setRoles(Set.of(defaultRole));

                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException("Oops! " + request.getEmail() + " already exists!"));
    }

    @Override
    public User registerUser(RegisterUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());

                    Role defaultRole = roleRepository.findByName("ROLE_USER")
                            .orElseThrow(() -> new RuntimeException("Role not found"));
                    user.setRoles(Set.of(defaultRole));

                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException("Oops! " + request.getEmail() + " already exists!"));
    }



    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());
                    if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
                    }
                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(
                userRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("User not found!");
                }
        );
    }

    @Override
    public UserDto convertUserToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}
