//UserController
package com.example.E_Comm.Controller;

import com.example.E_Comm.model.Cart;
import com.example.E_Comm.model.Category;
import com.example.E_Comm.model.OrderRequest;
import com.example.E_Comm.model.UserDetails;
import com.example.E_Comm.service.CartService;
import com.example.E_Comm.service.CategoryService;
import com.example.E_Comm.service.OrderService;
import com.example.E_Comm.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.logging.SocketHandler;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CartService cartService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrderService orderService;


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
            Integer countCart = cartService.getCountCart(userDetails.getId());
            m.addAttribute("countCart", countCart);

        }

        List<Category> allActiveCategory = categoryService.getAllActiveCategory();
        m.addAttribute("categorys", allActiveCategory);
    }

    private UserDetails getLoggedInUserDetails(Principal p) {
        String email = p.getName();
        UserDetails userDetails = userService.getUserByEmail(email);
        return userDetails;
    }


//----------------------Add to Cart-----------------------------------------------------

    @GetMapping("/addCart")
    public String addToCart(@RequestParam Integer pid, @RequestParam Integer uid, HttpSession session){

        Cart saveCart = cartService.saveCart(pid,uid);

        if (ObjectUtils.isEmpty(saveCart)){
            session.setAttribute("Error", "Failed!!");
        }else {
            session.setAttribute("Success", "Product added to cart");

        }

        return "redirect:/viewProducts/"+pid;
    }


    @GetMapping("/cart")
    public String loadCartPage(Principal p, Model m){

        String email = p.getName();
        UserDetails userDetails = userService.getUserByEmail(email);
        UserDetails user = getLoggedInUserDetails(p);
        List<Cart> carts = cartService.getCartByUser(user.getId());
        m.addAttribute("carts", carts);
        if (carts.size()>0) {
            double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
            m.addAttribute("totalOrderPrice", totalOrderPrice);
        }
        return "/user/cart";
    }


    @GetMapping("/cartQuantityUpdate")
    public String updateCartQuantity(@RequestParam String sy, @RequestParam Integer cid){

        cartService.updateQuantity(sy,cid);

        return "redirect:/user/cart";
    }


    @GetMapping("/orders")
    public String orderPage(){

        return "/user/order";
    }

    @PostMapping("/save-order")
    public String saveOrder(@ModelAttribute OrderRequest request,Principal p){

        //System.out.println(request);
        UserDetails user = getLoggedInUserDetails(p);
        orderService.saveOrder(user.getId(),request);

        return "/user/success";
    }



}