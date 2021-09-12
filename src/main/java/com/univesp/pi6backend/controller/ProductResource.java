package com.univesp.pi6backend.controller;

import com.univesp.pi6backend.repository.ProductEntity;
import com.univesp.pi6backend.repository.ProductEnum;
import com.univesp.pi6backend.repository.ProductJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductResource {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @GetMapping("/all")
    public List<ProductEntity> getProducts() {
        return productJpaRepository.findAll();
    }

    @GetMapping("/product")
    public ProductEntity getProduct(@RequestParam(name = "id") Long id) {
        return productJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductEntity> postProduct(@RequestBody ProductDTO productDTO,
                                                     UriComponentsBuilder uriBuilder) {
        ProductEntity entity;
        if (productDTO.getId() != null) {
            entity = productJpaRepository.findById(productDTO.getId()).orElseThrow(EntityNotFoundException::new);
        } else {
            entity = new ProductEntity();
        }
        entity.setProduct(productDTO.getProduct());
        entity.setPrice(productDTO.getPrice());
        entity.setSeller(productDTO.getSeller());
        productJpaRepository.save(entity);
        URI uri = uriBuilder.path("/products/product/?id={id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(entity);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/all")
    public void deleteAllProducts() {
        productJpaRepository.deleteAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/product")
    public void deleteProduct(@RequestParam(name = "id") Long id) {
        productJpaRepository.deleteById(id);
    }

}
