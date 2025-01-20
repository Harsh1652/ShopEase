//categoryService_impl.java
package com.example.E_Comm.service.categoryServiceImpl;

import com.example.E_Comm.model.Category;
import com.example.E_Comm.repository.CategoryRepository;
import com.example.E_Comm.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CategoryService_impl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public boolean existCategory(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public boolean deleteCategory(int id) {
        Category category = categoryRepository.findById(id).orElse(null);

        if (!ObjectUtils.isEmpty(category)){
            categoryRepository.delete(category);
            return true;
        }
        return false;
    }

    @Override
    public Category getCategoryById(int id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return category;
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getAllActiveCategory() {
        List<Category> categories = categoryRepository.findByIsActiveTrue();
        return categories;
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name); // Add a repository method for this
    }

}
