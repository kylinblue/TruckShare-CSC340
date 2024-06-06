package com.csc340.truckshare.webapp.controllers;

import com.csc340.truckshare.webapp.models.Conv;
import com.csc340.truckshare.webapp.services.ConvService;
import com.csc340.truckshare.webapp.services.ListingService;
import com.csc340.truckshare.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/conv") // Base URL for all methods in this controller


public class ConvController {
    @Autowired
    UserService userService; // Autowired dependency for UserService
    ListingService listingService;  // Autowired dependency for ListingService
    ConvService convService; // Autowired dependency for ConvService

    // Method to create a new conversation
    @GetMapping("/new")
    public String createConv(Conv conversation) {
        convService.saveConv(conversation);
        return "message-created";
    }

    // Method to update an existing conversation
    @GetMapping("/update")
    public String updateConv(Conv conversation) {
        convService.saveConv(conversation);
        return "message-saved";
    }
    /*
    public RedirectView createConversation(Conversation conversation, RedirectAttributes attributes) {
        conversationService.createConversation(conversation);
        attributes.addFlashAttribute("message", "New conversation started!");
        return new RedirectView("/webapp/user/userid/");
    }*/

    // Method to get all conversations by user ID
    @GetMapping("/user-id/{userid}/target/{userid2}")
    public String getConvByUserId(@PathVariable int userid, @PathVariable int userid2, Model model){
        model.addAttribute("conv", convService.getConvByUserId(userid, userid2));
        return "conversation";
    }

    // Method to get a conversation by its ID
    @GetMapping("/convid/{convid}")
    public Conv getConvById(int id) {
        return convService.getConvById(id);
    }

    // Method to delete a conversation
    @PostMapping("/delete")
    public String deleteConv(int id) {
        convService.deleteConv(id);
        return "conversation-deleted";
    }
    /*public RedirectView deleteConversation(@RequestParam("convId") int id, RedirectAttributes attributes) {
        conversationService.deleteConversation(id);
        attributes.addFlashAttribute("message", "Conversation deleted successfully!");
        return new RedirectView("/webapp/user/userid/");
    }*/
}