package com.csc340.truckshare.webapp.controllers;

import com.csc340.truckshare.webapp.models.*;
import com.csc340.truckshare.webapp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/msg") // Base URL for all methods in this controller

public class MessageController {
    @Autowired
    UserService userService;
    @Autowired
    ConvService conversationService;
    @Autowired
    MessageService messageService;
    @Autowired
    ListingService listingService;

    // Endpoint to send a message
    @PostMapping("/send")
    public String sendMessage(@ModelAttribute("message") Message message, Model model) {
        messageService.createMessage(message);
        model.addAttribute("convAttr",
                conversationService.getConvById(message.getConvId()));
        model.addAttribute("msgList",
                messageService.getMsgForConv(message.getConvId()));
        System.out.println(listingService.getListingById((message.getConvId())));
        model.addAttribute("user",
                userService.getUserByUserId(message.getSourceUserId()));
        return "redirect:/conv/conv-id/" + message.getConvId()
                + "/user/" + message.getSourceUserId();
    }

    // Endpoint to get messages for a specific conversation ID
    @GetMapping("/get/{id}")
    public List<Message> getMessage (int convId) {
        return messageService.getMsgForConv(convId);
    }// Return list of messages for the conversation ID
}
