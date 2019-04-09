package com.mrhmt.server.controllers;

import com.mrhmt.server.entities.Category;
import com.mrhmt.server.entities.Product;
import com.mrhmt.server.repositories.CategoryRepository;
import com.mrhmt.server.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @GetMapping(value="")
    Iterable<Category> readAll() {
        return categoryRepository.findAll();
    }

    @GetMapping(value="/{id}")
    Category read(@PathVariable int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    @GetMapping(value="/{id}/products")
    Iterable<Product> findCategoryProducts(@PathVariable int id) {
        Category category = new Category();
        category.setId(id);

        return productRepository.findProductsByCategory(category);
    }

    @PostMapping(value="")
    Category create(@RequestBody Category newCategory) {
        newCategory.setModified(Calendar.getInstance().getTime());
        return categoryRepository.save(newCategory);
    }

    @PutMapping(value="/{id}")
    Category update(@RequestBody Category updatingCategory, @PathVariable int id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(updatingCategory.getName());
                    category.setValid(updatingCategory.isValid());
                    category.setModified(Calendar.getInstance().getTime());

                    return categoryRepository.save(category);
                })
                .orElseGet(() -> {
                    updatingCategory.setId(id);

                    return categoryRepository.save(updatingCategory);
                });
    }

    @DeleteMapping(value="/{id}")
    void delete(@PathVariable int id) {
        categoryRepository.deleteById(id);
    }
}
