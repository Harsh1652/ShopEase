//ProductService.java
package com.example.E_Comm.service;

import com.example.E_Comm.model.Product;
import com.example.E_Comm.model.ProductOrder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    public Product saveProduct(Product product);

    public List<Product> getAllProduct();

    public Boolean deleteProduct(Integer id);

    public Product getProductById(Integer id);

    public Product updateProduct(Product product, MultipartFile file);

    public List<Product> getAllActiveProduct(String category);

    public List<Product> searchProduct(String ch);

}
