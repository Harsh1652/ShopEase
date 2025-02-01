//ProductRepository.java
package com.example.E_Comm.repository;

import com.example.E_Comm.model.Category;
import com.example.E_Comm.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {


    List<Product> findByIsActiveTrue();

    Page<Product> findByIsActiveTrue(Pageable pageable);

    List<Product> findByCategory(Category category);

    @Query("SELECT p FROM Product p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :ch, '%')) OR LOWER(p.category.name) LIKE LOWER(CONCAT('%', :ch, '%'))")
    List<Product> searchProduct(@Param("ch") String ch);

    Page<Product> findByCategory(Category category, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :ch, '%')) OR LOWER(p.category.name) LIKE LOWER(CONCAT('%', :ch, '%'))")
    Page<Product> searchProduct(@Param("ch") String ch, Pageable pageable);
}
