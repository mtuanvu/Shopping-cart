package com.shoppingCart.Shopping_cart.data;

import com.shoppingCart.Shopping_cart.model.User;
import com.shoppingCart.Shopping_cart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUserInfoNotExist();
    }

    private void createDefaultUserInfoNotExist() {
        for(int i = 1; i <= 5; i++){
            String defaultEmail = "user" + i + "@gmail.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }

            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword("123456");
            userRepository.save(user);
            System.out.println("Default vet user " + i + " created success");
        }
    }
}