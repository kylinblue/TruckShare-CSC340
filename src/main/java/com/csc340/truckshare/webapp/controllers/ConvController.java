package com.csc340.truckshare.webapp.controllers;

import com.csc340.truckshare.webapp.models.*;
import com.csc340.truckshare.webapp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/conv") // Base URL for all methods in this controller


public class ConvController {
    @Autowired
    UserService userService; // Autowired dependency for UserService
    @Autowired
    ListingService listingService;  // Autowired dependency for ListingService
    @Autowired
    ConvService convService; // Autowired dependency for ConvService
    @Autowired
    MessageService messageService;
/*
    // Method to create a new conversation
    @GetMapping("/new")
    public String createConv(Conv conversation) {
        convService.saveConv(conversation);
        return "message-created";
    } //TODO make it work and link it to user-homepage*/

    // Method to update an existing conversation
    @GetMapping("/update")
    public String updateConv(Conv conversation) {
        convService.saveConv(conversation);
        return "message-saved";
    } //TODO not yet functional, as one above
    /*
    public RedirectView createConversation(Conversation conversation, RedirectAttributes attributes) {
        conversationService.createConversation(conversation);
        attributes.addFlashAttribute("message", "New conversation started!");
        return new RedirectView("/webapp/user/userid/");
    }*/

    @GetMapping("/user-id/{userId}") // List of conversations of  a particular user
    public String getConvByUserId(@PathVariable int userId, Model model) {
        model.addAttribute("convList", convService.getConvByUserId(userId));
        model.addAttribute("user", userService.getUserByUserId(userId));
        return "user-conv";
    }

    @GetMapping("/user-1/{userId}/user-2/{userId2}/listing/{listingId}")
    public String getConvByUserPair(@PathVariable int userId, @PathVariable int userId2,
                                    @PathVariable int listingId,
                                    Model model){
        Listing listing = listingService.getListingById(listingId);
        Conv conv;
        if (listing.getConvId() == 0) {
            conv = convService.initConv(userId, userId2, listingId);
            convService.saveConv(conv);
            listing.setConvId(conv.getConvId());
            listingService.updateListing(listing);
            model.addAttribute("convAttr",
                    conv);
            model.addAttribute("user",
                    userService.getUserByUserId(userId));
            model.addAttribute("listing",
                    listingService.getListingById(listingId));
        }
        else {
            if (convService.getConvById(listing.getConvId()) == null){
                conv = convService.initConv(userId, userId2, listingId);
                convService.saveConv(conv);
                listing.setConvId(conv.getConvId());
                listingService.updateListing(listing);
            }
            else{
                conv = convService.getConvById(listing.getConvId());
                convService.saveConv(conv);
                listing.setConvId(conv.getConvId());
                listingService.updateListing(listing);
                model.addAttribute("msgList",
                        messageService.getMsgForConv(conv.getConvId()));
            }
            model.addAttribute("convAttr",
                    conv);
            model.addAttribute("listing",
                    listingService.getListingById(listingId));
            model.addAttribute("user",
                    userService.getUserByUserId(userId));
        }
        return "conv-message";
    }

    @GetMapping("/conv-id/{convId}/user/{userId}")
    public String getConvById(@PathVariable int convId, @PathVariable int userId, Model model) {
        Conv conv = convService.getConvById(convId);
        Listing listing = listingService.getListingById(conv.getListingId());
        model.addAttribute("listing",
                listing);
        model.addAttribute("convAttr", conv);
        model.addAttribute("msgList",
                messageService.getMsgForConv(conv.getConvId()));
        model.addAttribute("user", userService.getUserByUserId(userId));
        return "conv-message";
    }

    @GetMapping("/delete/{id}/user/{userId}/confirmed")
    public String deleteConversation(@PathVariable int id, @PathVariable int userId) {
        convService.deleteConv(id);
        return "redirect:/conv/user-id/{userId}";
    }

    // Method to delete a conversation
    @GetMapping("/delete/{id}/user/{userId}")
    public String deleteConfirmation(@PathVariable int id, @PathVariable int userId, Model model) {
        model.addAttribute("convAttr", convService.getConvById(id));
        model.addAttribute("user", userService.getUserByUserId(userId));
        return "conv-delete-confirmation";
    }


    /*public RedirectView deleteConversation(@RequestParam("convId") int id, RedirectAttributes attributes) {
        conversationService.deleteConversation(id);
        attributes.addFlashAttribute("message", "Conversation deleted successfully!");
        return new RedirectView("/webapp/user/userid/");
    }*/
}