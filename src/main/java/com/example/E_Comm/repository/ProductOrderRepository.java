//ProductOrderRepository.java
package com.example.E_Comm.repository;

import com.example.E_Comm.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder,Integer > {
    List<ProductOrder> findByUserId(Integer userId);
}
