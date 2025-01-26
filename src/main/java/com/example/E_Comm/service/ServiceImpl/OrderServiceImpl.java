//OrderServiceImpl
package com.example.E_Comm.service.ServiceImpl;

import com.example.E_Comm.Util.OrderStatus;
import com.example.E_Comm.model.Cart;
import com.example.E_Comm.model.OrderAddress;
import com.example.E_Comm.model.OrderRequest;
import com.example.E_Comm.model.ProductOrder;
import com.example.E_Comm.repository.CartRepository;
import com.example.E_Comm.repository.ProductOrderRepository;
import com.example.E_Comm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductOrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void saveOrder(Integer userid, OrderRequest orderRequest) {

        List<Cart> carts = cartRepository.findByUserId(userid);

        for (Cart cart:carts){
            ProductOrder order = new ProductOrder();
            order.setOrderId(UUID.randomUUID().toString());
            order.setOrderDate(LocalDate.now());
            order.setProduct(cart.getProduct());
            order.setPrice(cart.getProduct().getDiscountPrice());
            order.setQuantity(cart.getQuantity());
            order.setUser(cart.getUser());
            order.setStatus(OrderStatus.IN_PROGRESS.getName());
            order.setPaymentType(orderRequest.getPaymentType());

            OrderAddress address = new OrderAddress();
            address.setFirstName(orderRequest.getFirstName());
            address.setLastName(orderRequest.getLastName());
            address.setEmail(orderRequest.getEmail());
            address.setMobileNo(orderRequest.getMobileNo());
            address.setAddress(orderRequest.getAddress());
            address.setCity(orderRequest.getCity());
            address.setState(orderRequest.getState());
            address.setPincode(orderRequest.getPincode());

            order.setOrderAddress(address);

            orderRepository.save(order);
        }
    }

    @Override
    public List<ProductOrder> getOrdersByUser(Integer userId) {

        List<ProductOrder> orders = orderRepository.findByUserId(userId);

        return orders;
    }

    @Override
    public Boolean updateOrderStatus(Integer id, String status) {
        Optional<ProductOrder> findById = orderRepository.findById(id);
        if (findById.isPresent()) {
            ProductOrder productOrder = findById.get();
            productOrder.setStatus(status); // Set status passed as a parameter
            orderRepository.save(productOrder);
            return true;
        }
        return false;
    }

}
