package com.example.E_Comm.service;

import com.example.E_Comm.model.Cart;
import com.example.E_Comm.model.Product;
import com.example.E_Comm.model.UserDetails;
import com.example.E_Comm.repository.CartRepository;
import com.example.E_Comm.repository.ProductRepository;
import com.example.E_Comm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Cart saveCart(Integer productId, Integer userId) {

        UserDetails userDetails = userRepository.findById(userId).get();
        Product product = productRepository.findById(productId).get();

        Cart cartStatus = cartRepository.findByProductIdAndUserId(productId, userId);

        Cart cart = null;
        if (ObjectUtils.isEmpty(cartStatus)){
            cart = new Cart();
            cart.setProduct(product);
            cart.setUser(userDetails);
            cart.setQuantity(1);
            cart.setTotalprice(1 * product.getDiscountPrice());
        }else {
            cart = cartStatus;
            cart.setQuantity(cart.getQuantity()+1);
            cart.setTotalprice(cart.getQuantity() * cart.getProduct().getDiscountPrice());
        }

        Cart saveCart = cartRepository.save(cart);

        return saveCart;
    }

    @Override
    public List<Cart> getCartByUser(Integer userId) {
        return List.of();
    }


}
