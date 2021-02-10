package com.luizfernandotech.hruser.resources;

import com.luizfernandotech.hruser.entities.User;
import com.luizfernandotech.hruser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserRepository repository;

    @GetMapping(value = "/{id}")
    public User findById(@PathVariable Long id) {
        return repository.findById(id).get();
    }

    @GetMapping(value = "/search")
    public User findByEmail(@RequestParam String email) {
        return repository.findByEmail(email);
    }
}
