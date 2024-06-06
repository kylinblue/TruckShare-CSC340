package com.csc340.truckshare.webapp.controllers;

import com.csc340.truckshare.webapp.models.Listing;
import com.csc340.truckshare.webapp.repositories.ListingRepository;
import com.csc340.truckshare.webapp.services.ConvService;
import com.csc340.truckshare.webapp.services.ListingService;
import com.csc340.truckshare.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/listing")


public class ListingController {

    //RedirectAttributes attributes;
    @Autowired
    ListingService listingService;
    @Autowired
    UserService userService;
    @Autowired
    ConvService conversationService;

    @GetMapping("/all")
    public String getAllListingForUser(Model model) {
        model.addAttribute("allListing", listingService.getAllListings());
        return "all-listings";
    }


    @GetMapping("/userid/{userId}")
    public List<Listing> findListingByUserId(@PathVariable int userId){
        return listingService.queryByUserId(userId);
    }


    @GetMapping("/listing-id/{listingId}")
    public String findListingById(@PathVariable int listingId, Model model) {
        model.addAttribute("listing", listingService.getListingById(listingId));
        return "listing-detail";
    }

    @GetMapping("/user-id/{id}")
    public String findListingByUserId(@PathVariable int id, Model model){
        model.addAttribute("listings", listingService.queryByUserId(id));
        return "user-listings";
    }

    @PostMapping("/create")
    public String createListing(@RequestBody Listing listing){ //userId passed by front end
        listingService.createListing(listing);
        return "redirect:/listing/listing-id/" + listing.getListingId();
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image, @RequestParam("listingId") int listingId) {
        try {
            Listing listing = listingService.getListingById(listingId);
            listing.setImage(image.getBytes());
            listingService.updateListing(listing);
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public String updateListing(Listing listing){
        listingService.updateListing(listing);
        return "redirect:/listing/listing-id/" + listing.getListingId();
    }

    @GetMapping("/delete/{id}")
    public String deleteListing(@PathVariable int id){
        listingService.deleteListing(id);
        return "redirect:/listing/all";
    }

}

