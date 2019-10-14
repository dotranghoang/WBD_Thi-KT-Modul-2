package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public ModelAndView listAllCategory(Pageable pageable){
        Page<Category> categories = categoryService.findAll(pageable);

        ModelAndView modelAndView = new ModelAndView("/category/list");
        modelAndView.addObject("categories",categories);

        return modelAndView;
    }

    @GetMapping("/create-category")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category",new Category());

        return modelAndView;

    }

    @PostMapping("/save-category")
    public ModelAndView saveNote(@ModelAttribute Category category){

        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category",new Category());
        modelAndView.addObject("message","Created Category Successful!");

        return modelAndView;
    }

    @GetMapping("/edit-category/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Category category = categoryService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/category/edit");
        modelAndView.addObject("category",category);

        return modelAndView;
    }

    @PostMapping("/update-category")
    public ModelAndView updateNote(@ModelAttribute Category category){
        categoryService.save(category);

        ModelAndView modelAndView = new ModelAndView("/category/edit");
        modelAndView.addObject("category",category);
        modelAndView.addObject("message","Updated category successful ");

        return modelAndView;
    }

    @GetMapping("/delete-category/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Category category = categoryService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/category/delete");
        modelAndView.addObject("category",category);

        return modelAndView;
    }

    @PostMapping("/delete-category")
    public String deleteProduct(@ModelAttribute Category category){
        categoryService.remove(category.getId());

        return "redirect:category";
    }
}
