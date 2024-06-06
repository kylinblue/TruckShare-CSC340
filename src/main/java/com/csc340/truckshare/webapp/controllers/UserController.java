package com.csc340.truckshare.webapp.controllers;

import com.csc340.truckshare.webapp.models.User;
import com.csc340.truckshare.webapp.services.ConvService;
import com.csc340.truckshare.webapp.services.ListingService;
import com.csc340.truckshare.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")

public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ListingService listingService;
    @Autowired
    ConvService conversationService;

    @GetMapping("/login") // the login page
    public String userLogin(){
        return "user-login";
    }

    @GetMapping("/signup") // aka create
    public String userSignup(){
        return "user-signup";
    }

    @PostMapping("/create")
    public String createUser(@RequestBody User user, Model model) {
        // User: isAdmin? userPassword? username? firstName? lastName?
        if (userService.checkUserByUsername(user.getUsername())){
            model.addAttribute("response", "User exists");
            return "user-signup";
        }
        else {
            return "redirect:/user/user-id/" + userService.createUser(user);
        }
    }

    @PostMapping("/auth")
    public String authUser(@RequestBody User user, Model model) {
        User userInit = userService.getUserByUserName(user.getUsername(), user.getUserPassword());
        if (userInit!=null) {
            return "redirect:/user/user-id/" + userInit.getUserPassword();
        }
        else {
            model.addAttribute("response", "Wrong password");
            return "user-login";
        }
        /*int auth = userService.authUser(user.getUserId(), user.getUsername());
        if (auth == 0) {
            return "user-doesnt-exist"; // fixme fix these redirects
        }
        else if (auth == -1){
            return "wrong-password";
        }
        else {
            redirectAttributes.addAttribute("userId", user.getUserId());
            return "redirect:/user/{userId}";
        }*/
    }

    @GetMapping("/user-id/{userId}")
    public String userPage(@PathVariable int userId, Model model) {
        model.addAttribute("user", userService.getUserByUserId(userId));
        model.addAttribute("listingList", listingService.getAllListings());
        return "all-listings";
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
