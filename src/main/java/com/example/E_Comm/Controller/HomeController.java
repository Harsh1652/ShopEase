//HomeController.java
package com.example.E_Comm.Controller;

import com.example.E_Comm.model.Category;
import com.example.E_Comm.model.Product;
import com.example.E_Comm.model.UserDetails;
import com.example.E_Comm.repository.ProductRepository;
import com.example.E_Comm.service.CategoryService;
import com.example.E_Comm.service.ProductService;
import com.example.E_Comm.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void getUserDetails(Principal){

    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/signin")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String Register(){
        return "Register";
    }

    @GetMapping("/products")
    public String product(Model m, @RequestParam(value = "category", defaultValue = "") String categoryName) {
        List<Category> categories = categoryService.getAllCategory();
        List<Product> products = productService.getAllActiveProduct(categoryName);
        m.addAttribute("categories", categories);
        m.addAttribute("products", products);
        m.addAttribute("paramValue",categoryName);
        return "product";
    }


    @GetMapping("/viewProducts/{id}")
    public String viewProduct(@PathVariable int id, Model m){

        Product productById = productService.getProductById(id);
        m.addAttribute("product",productById);
        return "view_product";
    }

//---------------------------User-----------------------------

    @PostMapping("/saveUser")
    public String saveUser(UserDetails user,
                           @RequestParam("file") MultipartFile file,
                           HttpSession session) throws IOException {
        if (!file.isEmpty()) {
            // Save the uploaded file to the server
            File saveFile = new ClassPathResource("static/img").getFile();
            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "Profile" + File.separator + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Set the filename (not the file itself) in the database
            user.setProfileImage(file.getOriginalFilename());
        } else {
            // If no file is uploaded, use a default image
            user.setProfileImage("default.jpg");
        }

        // Save the user to the database
        UserDetails savedUser = userService.saveUser(user);

        if (!ObjectUtils.isEmpty(savedUser)) {
            session.setAttribute("Success", "User registered successfully!");
        } else {
            session.setAttribute("Error", "Something went wrong!");
        }

        return "redirect:/register";
    }



}
