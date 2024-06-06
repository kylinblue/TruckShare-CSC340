package com.csc340.truckshare.webapp.controllers;

import com.csc340.truckshare.webapp.models.Message;
import com.csc340.truckshare.webapp.services.ConvService;
import com.csc340.truckshare.webapp.services.MessageService;
import com.csc340.truckshare.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    // Endpoint to send a message
    @PostMapping("/send")
    public String sendMessage (Message message) {
        messageService.createMessage(message); // Create a new message
        return "message-sent"; // Return the view name
    }

    // Endpoint to get messages for a specific conversation ID
    @PostMapping("/getmessage")
    public List<Message> getMessage (int convId) {
        return messageService.getMsgForConv(convId);
    }// Return list of messages for the conversation ID
}
