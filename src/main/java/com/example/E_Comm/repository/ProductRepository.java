//ProductRepository.java
package com.example.E_Comm.repository;

import com.example.E_Comm.model.Category;
import com.example.E_Comm.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {


    List<Product> findByIsActiveTrue();

    List<Product> findByCategory(Category category);
}
