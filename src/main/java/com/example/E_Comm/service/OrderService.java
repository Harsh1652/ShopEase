//OrderService
package com.example.E_Comm.service;

import com.example.E_Comm.model.OrderRequest;
import com.example.E_Comm.model.ProductOrder;

import java.util.List;

public interface OrderService {

    public void saveOrder(Integer userid, OrderRequest orderRequest) throws Exception;

    public List<ProductOrder> getOrdersByUser(Integer userId);

    public ProductOrder updateOrderStatus(Integer id, String status);

    // public List<ProductOrder> getAllOrders();

    public List<ProductOrder> getAllOrdersSortedByDate();

    public ProductOrder getOrderByOrderId(String orderId);

}
