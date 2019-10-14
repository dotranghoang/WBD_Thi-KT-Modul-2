package com.codegym.service;

import com.codegym.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<Category> findAll(Pageable pageable);

    Iterable<Category> findAll();

    Category findById(Long id);

    void remove(Long id);

    void save(Category category);
}
