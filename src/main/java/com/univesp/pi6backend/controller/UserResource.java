package com.univesp.pi6backend.controller;

import com.univesp.pi6backend.repository.Usuario;
import com.univesp.pi6backend.repository.UsuarioJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserResource {

    @Autowired
    private UsuarioJpaRepository usuarioJpaRepository;

    @GetMapping("/all")
    public List<Usuario> getAllUsers() {
        return usuarioJpaRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/all")
    public void deleteAllUsers() {
        usuarioJpaRepository.deleteAll();
    }

}
