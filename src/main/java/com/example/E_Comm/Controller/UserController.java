//UserController
package com.example.E_Comm.Controller;

import com.example.E_Comm.Util.CommonUtil;
import com.example.E_Comm.Util.OrderStatus;
import com.example.E_Comm.model.*;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.security.PublicKey;
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

    @Autowired
    private CommonUtil commonUtil;


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


//----------------------Add to Cart & Orders-----------------------------------------------------

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
    public String orderPage(Principal p, Model m){

        UserDetails user = getLoggedInUserDetails(p);
        List<Cart> carts = cartService.getCartByUser(user.getId());
        m.addAttribute("carts", carts);
        if (carts.size()>0) {
            double orderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
            double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice() + 250 + 100;
            m.addAttribute("orderPrice", orderPrice);
            m.addAttribute("totalOrderPrice", totalOrderPrice);
        }

        return "/user/order";
    }

    @PostMapping("/save-order")
    public String saveOrder(@ModelAttribute OrderRequest request,Principal p) throws Exception {

        //System.out.println(request);
        UserDetails user = getLoggedInUserDetails(p);
        orderService.saveOrder(user.getId(),request);

        return "redirect:/user/success";
    }

    @GetMapping("/success")
    public String loadSuccess(){

        return "/user/success";
    }



    @GetMapping("/user-orders")
    public String myOrder(Model m, Principal p){

        UserDetails loginUser = getLoggedInUserDetails(p);
        List<ProductOrder> orders = orderService.getOrdersByUser(loginUser.getId());
        m.addAttribute("orders",orders);

        return "/user/my_orders";
    }


    @GetMapping("/update-status")
    public String updateOrderStatus(@RequestParam Integer id, @RequestParam Integer st, HttpSession session) throws Exception {

        OrderStatus[] values = OrderStatus.values();
        String status = null;
        for (OrderStatus orderSt:values){
            if (orderSt.getId().equals(st)) {
                status = orderSt.getName();
                break;
            }

        }
        ProductOrder updateOrder = orderService.updateOrderStatus(id,status);
        commonUtil.sendMailForProductOrder(updateOrder, status);

        if (!ObjectUtils.isEmpty(updateOrder)) {
            session.setAttribute("Success", "Order Cancelled Successfully");
        } else {
            session.setAttribute("Error", "Something went wrong!");
        }

        System.out.println("Values:"+values);

        return "redirect:/user/user-orders";
    }


//----------------------Profile--------------------------------------

    @GetMapping("/profile")
    public String profile(){

        return "/user/profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute UserDetails user,
                                @RequestParam(value = "image", required = false) MultipartFile image,
                                HttpSession session) throws IOException {

        UserDetails updateUserProfile = userService.updateUserProfile(user, image);
        if (ObjectUtils.isEmpty(updateUserProfile)){
            session.setAttribute("Error", "Profile not updated");
        } else {
            session.setAttribute("Success", "Profile updated");
        }

        return "redirect:/user/profile";
    }


}