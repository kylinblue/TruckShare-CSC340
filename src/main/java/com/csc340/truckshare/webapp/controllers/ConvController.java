package com.csc340.truckshare.webapp.controllers;

import com.csc340.truckshare.webapp.models.Conv;
import com.csc340.truckshare.webapp.services.ConvService;
import com.csc340.truckshare.webapp.services.ListingService;
import com.csc340.truckshare.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/conv")


public class ConvController {
    @Autowired
    UserService userService;
    ListingService listingService;
    ConvService convService;


    @GetMapping("/new")
    public String createConv(Conv conversation) {
        convService.saveConv(conversation);
        return "message-created";
    }

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

    @GetMapping("/userid/{userid}")
    public List<Conv> getConvByUserId(int id){
        return convService.getConvByUserId(id);
    }

    @GetMapping("/convid/{convid}")
    public Conv getConvById(int id) {
        return convService.getConvById(id);
    }

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