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
             return ResponseEntity.status(HttpStatus.CONFLICT).build();
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




    //Update Category
    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id,
                                                   @RequestBody Category updated) {

        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Category existing = optional.get();

        // basic validation
        if (updated.getName() == null || updated.getName().isBlank()
                || updated.getDescription() == null || updated.getDescription().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        // duplicate check by name (ignore same record)
        if (!existing.getName().equals(updated.getName())
                && categoryRepository.existsByName(updated.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // apply changes
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());

        // regenerate slug if name changed or slug empty
        String slug = updated.getSlug();
        if (slug == null || slug.isBlank() || !existing.getName().equals(updated.getName())) {
            slug = updated.getName()
                    .toLowerCase()
                    .replaceAll("[^a-z0-9]+", "-")
                    .replaceAll("(^-|-$)", "");
        }
        existing.setSlug(slug);

        Category saved = categoryRepository.save(existing);
        return ResponseEntity.ok(saved);
    }



    //Delete categories
    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity <Category> deleteCategory(@PathVariable Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

    }





    }









