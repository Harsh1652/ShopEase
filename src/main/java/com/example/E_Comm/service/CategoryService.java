//categoryService.java

package com.example.E_Comm.service;

import com.example.E_Comm.model.Category;

import java.util.List;

public interface CategoryService {



    public Category saveCategory(Category category);

    public boolean existCategory(String name);

    public List<Category> getAllCategory();

    public boolean deleteCategory(int id);

    public Category getCategoryById(int id);

    public List<Category> getAllActiveCategory();

    Category getCategoryByName(String name);

}
