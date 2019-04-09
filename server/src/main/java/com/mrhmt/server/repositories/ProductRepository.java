package com.mrhmt.server.repositories;

import com.mrhmt.server.entities.Category;
import com.mrhmt.server.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findProductsByCategory(Category category);
}
