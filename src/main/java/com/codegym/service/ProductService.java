package com.codegym.service;

import com.codegym.model.Category;
import com.codegym.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<Product> findAll(Pageable pageable);

    Product findById(Long id);

    void remove(Long id);

    void save(Product product);

    Page<Product> findAllByNameContaining(String name, Pageable pageable);
    Page<Product> findAllByCategory(Category category, Pageable pageable);
    Page<Product> findAllByCategoryAndNameContaining(Category category,String name,Pageable pageable);
}
