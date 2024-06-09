package com.csc340.truckshare.webapp.controllers;

import com.csc340.truckshare.webapp.models.User;
import com.csc340.truckshare.webapp.services.ConvService;
import com.csc340.truckshare.webapp.services.ListingService;
import com.csc340.truckshare.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String userLogin(Model model){
        model.addAttribute("user", new User());
        return "user-login";
    }

    @GetMapping("/signup") // aka create
    public String userSignup(Model model){
        model.addAttribute("user", new User());

        return "user-signup";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user, Model model) {
        // User: isAdmin? userPassword? getUsername? firstName? lastName?
        if (userService.getUserByUserName(user.getUsername())!=null) {
            model.addAttribute("exists", "User exists");
            return "user-signup";
        }
        else {
            user.setUserType(1);
            return "redirect:/user/user-id/" + userService.saveUser(user);
        }
    }

    @PostMapping("/auth")
    public String authUser(@ModelAttribute("user") User user, Model model) {
        User authUser = userService.getUserByUserName(user.getUsername());
        if (authUser!=null) {
            return "redirect:/user/user-id/" + authUser.getUserId();
        }
        else {
            model.addAttribute("invalid", "invalid combination");
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
        return "user-homepage";
    }

    @GetMapping("/guest")
    public String guestPage(Model model) {
        User guest = new User();
        guest.setUserId(0);
        guest.setUserType(0);
        model.addAttribute("user", guest);
        model.addAttribute("listingList", listingService.getAllListings());
        return "user-homepage";
    }

    /*@PostMapping("/auth")
    public String userAuth(String getUsername, String password){
        User user = userService.getUserByUserName(getUsername, password);
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
    public String userLogout(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("logout", "logout");
        return "user-login";
    }
}
