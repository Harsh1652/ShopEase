//OrderService
package com.example.E_Comm.service;

import com.example.E_Comm.model.OrderRequest;
import com.example.E_Comm.model.ProductOrder;

public interface OrderService {

    public void saveOrder(Integer userid, OrderRequest orderRequest);

}
