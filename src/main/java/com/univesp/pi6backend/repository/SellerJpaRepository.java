package com.univesp.pi6backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.security.PrivateKey;
import java.util.Optional;

public interface SellerJpaRepository  extends JpaRepository<Seller, Long> {

    Optional<Seller> findByNome(String nome);

}
