package com.univesp.pi6backend.controller;

import com.univesp.pi6backend.repository.UserJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserResource {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userJpaRepository.findAll();
    }
}
