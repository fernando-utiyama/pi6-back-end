package com.univesp.pi6backend.controller;

import com.univesp.pi6backend.repository.ProductEntity;
import com.univesp.pi6backend.repository.ProductJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductResource {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @GetMapping("/all")
    public List<ProductEntity> getAllProducts() {
        return productJpaRepository.findAll().stream()
                .sorted(Comparator.comparing(ProductEntity::getPrice))
                .collect(Collectors.toList());
    }

    @GetMapping("/product/{id]")
    public ProductEntity getProduct(@PathVariable(name = "id") Long id) {
        return productJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductEntity> postProduct(@RequestBody ProductDTO productDTO,
                                                     UriComponentsBuilder uriBuilder) {
        ProductEntity entity;
        if (productDTO.getId() != null) {
            entity = productJpaRepository.findById(productDTO.getId()).orElse(new ProductEntity());
            entity.setId(productDTO.getId());
        } else {
            entity = new ProductEntity();
        }
        entity.setProduct(productDTO.getProduct());
        entity.setPrice(productDTO.getPrice());
        entity.setSeller(productDTO.getSeller());
        entity.setAmong(productDTO.getAmong());
        productJpaRepository.save(entity);
        URI uri = uriBuilder.path("/products/product/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(entity);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long id,
                                       @RequestBody ProductDTO productDTO,
                                       UriComponentsBuilder uriBuilder) {

        ProductEntity productEntity = productJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        productEntity.setProduct(productDTO.getProduct());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setSeller(productDTO.getSeller());
        productEntity.setAmong(productDTO.getAmong());
        productJpaRepository.save(productEntity);
        URI uri = uriBuilder.path("/products/product/{id}").buildAndExpand(productEntity.getId()).toUri();
        return ResponseEntity.created(uri).body(productEntity);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable(name = "id") Long id) {
        productJpaRepository.deleteById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/all")
    public void deleteAllProducts() {
        productJpaRepository.deleteAll();
    }

}
