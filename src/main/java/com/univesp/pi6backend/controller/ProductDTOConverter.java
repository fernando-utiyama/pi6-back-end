package com.univesp.pi6backend.controller;

import com.univesp.pi6backend.repository.Product;
import com.univesp.pi6backend.repository.User;

import java.util.ArrayList;
import java.util.List;

public class ProductDTOConverter {

    public static List<ProductDTO> productsToProductsDto(List<Product> product) {
        if (product == null) {
            return null;
        }

        List<ProductDTO> list = new ArrayList<ProductDTO>(product.size());
        for (Product product1 : product) {
            list.add(productToProductDto(product1));
        }

        return list;
    }

    public static ProductDTO productToProductDto(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setSeller(productUserName(product));
        productDTO.setId(product.getId());
        productDTO.setProduct(product.getProduct());
        productDTO.setPrice(product.getPrice());
        productDTO.setAmong(product.getQuantity());

        return productDTO;
    }

    private static String productUserName(Product product) {
        if (product == null) {
            return null;
        }
        User user = product.getUser();
        if (user == null) {
            return null;
        }
        String name = user.getName();
        if (name == null) {
            return null;
        }
        return name;
    }
}



