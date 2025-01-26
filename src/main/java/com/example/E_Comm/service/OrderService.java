//OrderService
package com.example.E_Comm.service;

import com.example.E_Comm.model.OrderRequest;
import com.example.E_Comm.model.ProductOrder;

import java.util.List;

public interface OrderService {

    public void saveOrder(Integer userid, OrderRequest orderRequest);

    public List<ProductOrder> getOrdersByUser(Integer userId);

    public Boolean updateOrderStatus(Integer id, String status);

}
