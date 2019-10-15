package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.model.Product;
import com.codegym.service.CategoryService;
import com.codegym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

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

    @RequestMapping("/")
    public String home(){
        return "redirect:product";
    }

    @GetMapping("/product")
    public ModelAndView listAllProduct(@PageableDefault(value = 10,sort = "category") Pageable pageable){
        Page<Product> products = productService.findAll(pageable);

        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products",products);

        return modelAndView;
    }

    @PostMapping("/product")
    public ModelAndView listAllNote(@RequestParam("s") Optional<String> s, @RequestParam("id") Long id,
                                    @PageableDefault(value = 10,sort = "category") Pageable pageable) {
        Page<Product> products;
        Category category = categoryService.findById(id);

        if (s.isPresent() && category == null) {
            products = productService.findAllByNameContaining(s.get(), pageable);
        } else if (s.isPresent() && category != null) {
            products = productService.findAllByCategoryAndNameContaining(category, s.get(), pageable);
        } else if (!s.isPresent() && category != null) {
            products = productService.findAllByCategory(category, pageable);
        } else {
            products = productService.findAll(pageable);
        }
            ModelAndView modelAndView = new ModelAndView("/product/list");
            modelAndView.addObject("products", products);

        return modelAndView;
    }

    @GetMapping("/create-product")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product",new Product());

        return modelAndView;

    }

    @PostMapping("/save-product")
    public ModelAndView saveNote(@Validated @ModelAttribute Product product, BindingResult bindingResult){

        if (bindingResult.hasFieldErrors()) {
            ModelAndView modelAndView = new ModelAndView("/product/create");
            return modelAndView;
        }

        String date = String.valueOf(java.time.LocalDateTime.now());
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
    public ModelAndView updateNote(@Validated @ModelAttribute Product product, BindingResult bindingResult){

        if (bindingResult.hasFieldErrors()) {
            ModelAndView modelAndView = new ModelAndView("/product/edit");
            return modelAndView;
        }

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
    public ModelAndView readDetailProduct(@PathVariable Long id) {
        Product product = productService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/product/read");
        modelAndView.addObject("product",product);

        return modelAndView;
    }

}
