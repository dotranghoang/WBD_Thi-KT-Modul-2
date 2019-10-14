package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.model.Product;
import com.codegym.service.CategoryService;
import com.codegym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }

    @GetMapping("/product")
    public ModelAndView listAllProduct(Pageable pageable){
        Page<Product> products = productService.findAll(pageable);

        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products",products);

        return modelAndView;
    }

    @GetMapping("/create-product")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product",new Product());

        return modelAndView;

    }

    @PostMapping("/save-product")
    public ModelAndView saveNote(@ModelAttribute Product product){

        String date = String.valueOf(LocalDate.now());
        product.setDayCreate(date);
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product",new Product());
        modelAndView.addObject("message","Created Product Successful!");

        return modelAndView;
    }

    @GetMapping("/edit-product/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Product product = productService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/product/edit");
        modelAndView.addObject("product",product);

        return modelAndView;
    }

    @PostMapping("/update-product")
    public ModelAndView updateNote(@ModelAttribute Product product){
        productService.save(product);

        ModelAndView modelAndView = new ModelAndView("/product/edit");
        modelAndView.addObject("product",product);
        modelAndView.addObject("message","Updated product successful ");

        return modelAndView;
    }

    @GetMapping("/delete-product/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Product product = productService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/product/delete");
        modelAndView.addObject("product",product);

        return modelAndView;
    }

    @PostMapping("/delete-product")
    public String deleteProduct(@ModelAttribute Product product){
        productService.remove(product.getId());

        return "redirect:product";
    }

    @GetMapping("/read-product/{id}")
    public ModelAndView readDetailNote(@PathVariable Long id) {
        Product product = productService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/product/read");
        modelAndView.addObject("product",product);

        return modelAndView;
    }

}
