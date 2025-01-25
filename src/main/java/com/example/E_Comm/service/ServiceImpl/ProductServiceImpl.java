//ProductServiceImpl.java
package com.example.E_Comm.service.ServiceImpl;

import com.example.E_Comm.model.Category;
import com.example.E_Comm.model.Product;
import com.example.E_Comm.repository.ProductRepository;
import com.example.E_Comm.service.CategoryService;
import com.example.E_Comm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;


    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Boolean deleteProduct(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        if (!ObjectUtils.isEmpty(product)){
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    @Override
    public Product getProductById(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        return product;
    }

    @Override
    public Product updateProduct(Product product, MultipartFile image) {

        Product dbProduct = getProductById(product.getId());

        if (dbProduct == null) {
            return null; // Handle the case where the product doesn't exist
        }

        String imageName = image.isEmpty() ? dbProduct.getImage() : image.getOriginalFilename();

        // Update product fields
        dbProduct.setTitle(product.getTitle());
        dbProduct.setDescription(product.getDescription());
        dbProduct.setPrice(product.getPrice());
        dbProduct.setStock(product.getStock());
        dbProduct.setImage(imageName);
        dbProduct.setIsActive(product.getIsActive());

        dbProduct.setDiscount(product.getDiscount());

        double discount  = product.getPrice()*(product.getDiscount()/100.0);
        double discountPrice = product.getPrice()-discount;

        dbProduct.setDiscountPrice(discountPrice);

        // Fetch and set the Category object using the category ID from the form
        if (product.getCategory() != null) {
            dbProduct.setCategory(product.getCategory());
        }

        // Save the updated product to the database
        Product updatedProduct = productRepository.save(dbProduct);

        if (!ObjectUtils.isEmpty(updatedProduct) && !image.isEmpty()) {
            try {
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "Products" + File.separator + image.getOriginalFilename());
                Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return updatedProduct;
    }

    @Override
    public List<Product> getAllActiveProduct(String categoryName) {
        if (ObjectUtils.isEmpty(categoryName)) {
            return productRepository.findByIsActiveTrue();
        } else {
            // Fetch the Category object based on the category name
            Category category = categoryService.getCategoryByName(categoryName); // Implement this method in CategoryService
            if (category != null) {
                return productRepository.findByCategory(category);
            } else {
                return List.of(); // Return an empty list if the category is not found
            }
        }
    }

}
