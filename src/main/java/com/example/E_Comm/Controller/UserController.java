//UserController
package com.example.E_Comm.Controller;

import com.example.E_Comm.model.Category;
import com.example.E_Comm.model.UserDetails;
import com.example.E_Comm.service.CategoryService;
import com.example.E_Comm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/home")
    public String userHome() {
        return "user/home";  // Ensure 'user/home.html' exists
    }

    @ModelAttribute
    public void getUserDetails(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            UserDetails userDetails = userService.getUserByEmail(email);
            m.addAttribute("user", userDetails);

        }

        List<Category> allActiveCategory = categoryService.getAllActiveCategory();
        m.addAttribute("categorys", allActiveCategory);
    }

    private UserDetails getLoggedInUserDetails(Principal p) {
        String email = p.getName();
        UserDetails userDetails = userService.getUserByEmail(email);
        return userDetails;
    }


}