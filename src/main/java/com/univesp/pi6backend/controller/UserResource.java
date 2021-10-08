package com.univesp.pi6backend.controller;

import com.univesp.pi6backend.repository.Usuario;
import com.univesp.pi6backend.repository.UsuarioJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
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

    @GetMapping("/user/{id]")
    public Usuario getUser(@PathVariable(name = "id") Long id) {
        return usuarioJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/all")
    public void deleteAllUsers() {
        usuarioJpaRepository.deleteAll();
    }

    @PostMapping("/create")
    public ResponseEntity<Usuario> postUser(@RequestParam String user,
                                               UriComponentsBuilder uriBuilder) {
        Usuario entity = new Usuario(user);
        usuarioJpaRepository.save(entity);
        URI uri = uriBuilder.path("/users/user/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(entity);
    }

}
