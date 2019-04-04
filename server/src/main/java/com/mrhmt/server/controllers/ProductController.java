package com.mrhmt.server.controllers;

import com.mrhmt.server.entities.Product;
import com.mrhmt.server.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    // GET list items
    @GetMapping("")
    Iterable<Product> readAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    Product read(@PathVariable int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    @PostMapping("")
    Product create(@RequestBody Product newProduct) {
        return productRepository.save(newProduct);
    }

    @PutMapping("/{id}")
    Product update(@RequestBody Product updatingProduct, @PathVariable int id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatingProduct.getName());
                    product.setDescription(updatingProduct.getDescription());
                    product.setPrice(updatingProduct.getPrice());
                    product.setQuantity(updatingProduct.getQuantity());
                    product.setThumbnail(updatingProduct.getThumbnail());
                    product.setCategory(updatingProduct.getCategory());
                    product.setSupplier(updatingProduct.getSupplier());
                    product.setValid(updatingProduct.isValid());
                    product.setModified(Calendar.getInstance().getTime());

                    return productRepository.save(product);
                })
                .orElseGet(() -> {
                    updatingProduct.setId(id);

                    return productRepository.save(updatingProduct);
                });
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        productRepository.deleteById(id);
    }
}
