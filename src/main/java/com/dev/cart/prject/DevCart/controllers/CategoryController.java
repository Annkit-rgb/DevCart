package com.dev.cart.prject.DevCart.controllers;



import com.dev.cart.prject.DevCart.models.Category;
import com.dev.cart.prject.DevCart.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
private CategoryRepository categoryRepository;


// Get All Categories
    @GetMapping("/public/categories")
    public  List<Category> getAllCategories() {
        return  categoryRepository.findAll();
    }

    //Get All Categories Via Id
    @GetMapping("/public/categories/{id}")
    public ResponseEntity <Category> getCategoriesById(@PathVariable Long id) {
       Optional<Category> category = categoryRepository.findById(id);

       if(category.isPresent()) {
            return ResponseEntity.ok().body(category.get());
       }else  {
            return ResponseEntity.notFound().build();
       }
    }


    //create category
    @PostMapping("/admin/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {

        // basic null / blank checks
        if (category.getName() == null || category.getName().isBlank()
                || category.getDescription() == null || category.getDescription().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (categoryRepository.existsByName(category.getName())) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
         }

        // optional: generate slug if you use it
        if (category.getSlug() == null || category.getSlug().isBlank()) {
            String slug = category.getName()
                    .toLowerCase()
                    .replaceAll("[^a-z0-9]+", "-")
                    .replaceAll("(^-|-$)", "");
            category.setSlug(slug);
        }

        Category saved = categoryRepository.save(category);
        return ResponseEntity.ok(saved);
    }










}
