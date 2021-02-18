package com.luizfernandotech.hroauth.services;

import com.luizfernandotech.hroauth.entities.User;
import com.luizfernandotech.hroauth.feignclients.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserFeignClient userFeignClient;

    public User findByEmail(String email) {

        User user = userFeignClient.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Email not found");
        }

        return user;
    }
}
