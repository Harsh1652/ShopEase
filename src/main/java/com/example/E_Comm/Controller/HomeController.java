//HomeController.java
package com.example.E_Comm.Controller;

import com.example.E_Comm.Util.CommonUtil;
import com.example.E_Comm.model.Category;
import com.example.E_Comm.model.Product;
import com.example.E_Comm.model.UserDetails;
import com.example.E_Comm.repository.ProductRepository;
import com.example.E_Comm.service.CartService;
import com.example.E_Comm.service.CategoryService;
import com.example.E_Comm.service.ProductService;
import com.example.E_Comm.service.UserService;
import com.sun.net.httpserver.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.UUID;


@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private CartService cartService;



    @ModelAttribute
    public void getUserDetails(Principal p,Model m){

        if (p!=null){
           String email = p.getName();
           UserDetails userDetails = userService.getUserByEmail(email);
           m.addAttribute("user", userDetails);
           Integer countCart = cartService.getCountCart(userDetails.getId());
           m.addAttribute("countCart", countCart);
        }

        List<Category> allActiveCategory = categoryService.getAllActiveCategory();
        m.addAttribute("category", allActiveCategory);
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/signin")
    public String login() {
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



//----------------------Forgot Password----------------------------------

    @GetMapping("/forgot-password")
    public String showForgotPassword(){
        return "forgot_password.html";
    }




    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, HttpSession session, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        UserDetails userByEmail = userService.getUserByEmail(email);
        if (ObjectUtils.isEmpty(userByEmail)) {
            session.setAttribute("Error", "Invalid email");
        } else {
            String resetToken = UUID.randomUUID().toString();
            userService.updateUserResetToken(email, resetToken);

            // Generate URL to send in the mail to reset password
            String url = commonUtil.generateUrl(request) + "/reset-password?token=" + resetToken;

            Boolean sendMail = commonUtil.sendMail(url, email);
            if (sendMail) {
                session.setAttribute("Success", "Please check your email. Password reset link has been sent.");
            } else {
                session.setAttribute("Error", "Something went wrong! Email not sent.");
            }
        }

        return "redirect:/forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPassword(@RequestParam String token, HttpSession session, Model m){

        UserDetails userByToken = userService.getUserByToken(token);
        if (userByToken == null){
            m.addAttribute("Message","Your link is invalid or expired");
            return "Message";
        }
        m.addAttribute("token", token);
        return "reset_password.html";
    }



    @PostMapping("/reset-password")
    public String showResetPassword(@RequestParam String token,@RequestParam String password, HttpSession session, Model m){

        UserDetails userByToken = userService.getUserByToken(token);
        if (userByToken == null){
            m.addAttribute("Error","Your link is invalid or expired");
            return "Message";
        }else {
            userByToken.setPassword(passwordEncoder.encode(password));
            userByToken.setResetToken(null);
            userService.updateUser(userByToken);
            session.setAttribute("Success", "Password changed successfully");
            m.addAttribute("Message","Password changed successfully");
            return "Message";
        }
    }


}
