package com.univesp.pi6backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByName(String name);

}
