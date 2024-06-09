package com.csc340.truckshare.webapp.controllers;

import com.csc340.truckshare.webapp.models.Listing;
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

    /*@GetMapping("/all")
    public String getAllListingForUser(Model model) {
        model.addAttribute("allListing", listingService.getAllListings());
        return "all-listings";
    }*/


    @GetMapping("/userid/{userId}")
    public List<Listing> findListingByUserId(@PathVariable int userId){
        return listingService.queryByUserId(userId);
    }


    @GetMapping("/listing-id/{listingId}/user-id/{userId}") // A particular listing
    public String findListingById(@PathVariable int listingId, @PathVariable int userId, Model model) {
        model.addAttribute("listing", listingService.getListingById(listingId));
        model.addAttribute("user", userService.getUserByUserId(userId));
        return "listing-detail";
    }

    @GetMapping("/user-id/{id}") // All listings for a particular user
    public String findListingByUserId(@PathVariable int id, Model model){
        model.addAttribute("listings", listingService.queryByUserId(id));
        model.addAttribute("user", userService.getUserByUserId(id));
        return "user-listings";
    }

    /*@GetMapping("/get-getUsername/{id}")
    public String getUsername(@PathVariable int id) {
        return userService.getUserByUserId(id).getUsername();
    }*/

    @GetMapping("/form/user/{id}")
    public String listingForm(@PathVariable int id, Model model)
    {
        Listing newListing = new Listing();
        newListing.setUserId(id);
        newListing.setNewness(true);
        model.addAttribute("listing", newListing);
        model.addAttribute("user", userService.getUserByUserId(id));
        return "listing-form";
    }

    @GetMapping("/form/{id}/user/{userId}")
    public String existingForm(@PathVariable int id, @PathVariable int userId, Model model) {
        model.addAttribute("listing", listingService.getListingById(id));
        model.addAttribute("user", userService.getUserByUserId(userId));
        return "listing-form";
    }

    @PostMapping("/save")
    public String createListing(@ModelAttribute("listing") Listing listing){ //userId passed by front end
        listingService.createListing(listing);
        return "redirect:/listing/listing-id/" + listing.getListingId() + "/user-id/" + listing.getUserId();
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

    @GetMapping("/delete/{id}/user/{userId}/confirmed")
    public String deleteListing(@PathVariable int id, @PathVariable int userId){
        listingService.deleteListing(id);
        return "redirect:/listing/user-id/{userId}";
    }

    @GetMapping("/delete/{id}/user/{userId}")
    public String deleteConfirmation(@PathVariable int id, @PathVariable int userId, Model model) {
        model.addAttribute("listing", listingService.getListingById(id));
        model.addAttribute("user", userService.getUserByUserId(userId));
        return "listing-delete-confirmation";
    }

}

