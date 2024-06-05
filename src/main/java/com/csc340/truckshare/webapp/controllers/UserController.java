package com.csc340.truckshare.webapp.controllers;

import com.csc340.truckshare.webapp.models.User;
import com.csc340.truckshare.webapp.services.ConvService;
import com.csc340.truckshare.webapp.services.ListingService;
import com.csc340.truckshare.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")

public class UserController {
    @Autowired
    UserService userService;
    ListingService listingService;
    ConvService conversationService;

    @GetMapping("/login") // the login page
    public String userLogin(){
        return "user-login";
    }

    @GetMapping("/signup") // aka create
    public String userSignup(){
        return "user-signup";
    }

    @PostMapping("/auth")
    public String createUser(@RequestBody User user, @RequestBody boolean register, RedirectAttributes redirectAttributes) {
        if(register){
            if (userService.createUser(user)==-1){
                return "user-exists";
            }
            else {
                redirectAttributes.addAttribute("userId", user.getUserId());
                return "redirect:/user/{userId}";
            }
        }
        else{
            int auth = userService.authUser(user.getUserId(), user.getUsername());
            if (auth == 0) {
                return "user-doesnt-exist"; // fixme fix these redirects
            }
            else if (auth == -1){
                return "wrong-password";
            }
            redirectAttributes.addAttribute("userId", user.getUserId());
            return "redirect:/user/{userId}";
        }
    }

    /*@PostMapping("/auth")
    public String userAuth(String username, String password){
        User user = userService.getUserByUserName(username, password);
        if (user == null)
        {
            return "auth-failure";
        }
        else if (user.getUserType()) {
            return "admin-home";
        }
        else return "user-home";
    }*/

    @GetMapping("/logout")
    public String userLogout(){ return "redirect:/"; }
}
