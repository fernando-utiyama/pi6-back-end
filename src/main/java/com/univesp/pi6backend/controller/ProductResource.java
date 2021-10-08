package com.univesp.pi6backend.controller;

import com.univesp.pi6backend.repository.Product;
import com.univesp.pi6backend.repository.ProductJpaRepository;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductResource {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private UsuarioJpaRepository usuarioJpaRepository;

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/all")
    public List<ProductDTO> getAllProducts() {
        return ProductDTOConverter.productsToProductsDto(productJpaRepository.findAll()).stream()
                .sorted(Comparator.comparing(ProductDTO::getPrice))
                .collect(Collectors.toList());
    }

    @GetMapping("/product/{id]")
    public Product getProduct(@PathVariable(name = "id") Long id) {
        return productJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> postProduct(@RequestBody ProductDTO productDTO,
                                               UriComponentsBuilder uriBuilder) {
        Product entity;
        if (productDTO.getId() != null) {
            entity = productJpaRepository.findById(productDTO.getId()).orElse(new Product());
            entity.setId(productDTO.getId());
        } else {
            entity = new Product();
        }
        entity.setProduct(productDTO.getProduct());
        entity.setPrice(productDTO.getPrice());
        entity.setQuantity(productDTO.getQuantity());

        Usuario usuario;
        Optional<Usuario> optionalUser = usuarioJpaRepository.findByName(productDTO.getSeller());
        usuario = optionalUser.orElseGet(() ->
                usuarioJpaRepository.save(new Usuario(productDTO.getSeller())));
        entity.setUsuario(usuario);

        productJpaRepository.save(entity);
        entityManager.clear();

        URI uri = uriBuilder.path("/products/product/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(ProductDTOConverter.productToProductDto(entity));
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,
                                                 @RequestBody ProductDTO productDTO,
                                                 UriComponentsBuilder uriBuilder) {

        Product product = productJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        product.setProduct(productDTO.getProduct());
        product.setPrice(productDTO.getPrice());
        product.setUsuario(usuarioJpaRepository.findByName(productDTO.getSeller()).orElseThrow(EntityNotFoundException::new));
        product.setQuantity(productDTO.getQuantity());
        productJpaRepository.save(product);
        URI uri = uriBuilder.path("/products/product/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(ProductDTOConverter.productToProductDto(product));
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
