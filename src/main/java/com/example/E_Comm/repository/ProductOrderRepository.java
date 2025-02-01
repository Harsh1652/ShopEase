//ProductOrderRepository.java
package com.example.E_Comm.repository;

import com.example.E_Comm.model.ProductOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder,Integer > {
    List<ProductOrder> findByUserId(Integer userId);

    @Query("SELECT o FROM ProductOrder o ORDER BY o.orderDate DESC")
    List<ProductOrder> findAllOrdersSortedByDate();

    @Query("SELECT o FROM ProductOrder o ORDER BY o.orderDate DESC")
    Page<ProductOrder> findAllOrdersSortedByDate(Pageable pageable);

    ProductOrder findByOrderId(String orderId);
}
