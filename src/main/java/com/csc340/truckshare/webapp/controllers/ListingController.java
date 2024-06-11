package com.csc340.truckshare.webapp.controllers;

import com.csc340.truckshare.webapp.models.*;
import com.csc340.truckshare.webapp.services.*;
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


    @Autowired
    ListingService listingService; // Autowired dependency  for listingService
    @Autowired
    UserService userService; // Autowired dependency  for userService
    @Autowired
    ConvService conversationService; // Autowired dependency for conversationService

    /*@GetMapping("/all")
    public String getAllListingForUser(Model model) {
        model.addAttribute("allListing", listingService.getAllListings());
        return "all-listings";
    }*/

    /*
    @GetMapping("/userid/{userId}")
    public List<Listing> findListingByUserId(@PathVariable int userId){
        return listingService.queryByUserId(userId);
    }*/


    /**
     * Get details of a particular listing by listing ID and user ID.
     * @param listingId the ID of the listing
     * @param userId the ID of the user
     * @param model the model to hold attributes
     * @return the listing detail view
     */
    @GetMapping("/listing-id/{listingId}/user-id/{userId}") // A particular listing
    public String findListingById(@PathVariable int listingId, @PathVariable int userId, Model model) {
        model.addAttribute("listing", listingService.getListingById(listingId));
        model.addAttribute("user", userService.getUserByUserId(userId));
        return "listing-detail";
    }

    /**
     * Get all listings for a particular user by user ID.
     * @param id the ID of the user
     * @param model the model to hold attributes
     * @return the user listings view
     */
    @GetMapping("/user-id/{id}") // All listings for a particular user
    public String findListingByUserId(@PathVariable int id, Model model){
        model.addAttribute("listings", listingService.queryByUserId(id));
        model.addAttribute("user", userService.getUserByUserId(id));
        return "user-listings";
    }

    /**
     * Get all listings reserved by a particular user.
     * @param id the ID of the user
     * @param model the model to hold attributes
     * @return the user reservations view
     */
    @GetMapping("/reservations/user-id/{id}")
    public String findListingReservedByUserId(@PathVariable int id, Model model) {
        List<Listing> allListing = listingService.getAllListings();
        allListing.removeIf(listing -> listing.getReserveUserId()!=id);
        model.addAttribute("listings", allListing);
        model.addAttribute("user", userService.getUserByUserId(id));
        return "user-reservations";
    }

    /*@GetMapping("/get-getUsername/{id}")
    public String getUsername(@PathVariable int id) {
        return userService.getUserByUserId(id).getUsername();
    }*/

    /**
     * Get the form for creating a new listing for a user.
     * @param id the ID of the user
     * @param model the model to hold attributes
     * @return the listing form view
     */
    @GetMapping("/form/user/{id}")
    public String listingForm(@PathVariable int id, Model model)
    {
        Listing newListing = new Listing();
        newListing.setUserId(id);
        newListing.setNewness(true);
        newListing.setStatus(0);
        model.addAttribute("listing", newListing);
        model.addAttribute("user", userService.getUserByUserId(id));
        return "listing-form";
    }

    /**
     * Get the form for editing an existing listing.
     * @param id the ID of the listing
     * @param userId the ID of the user
     * @param model the model to hold attributes
     * @return the listing form view
     */
    @GetMapping("/form/{id}/user/{userId}")
    public String existingForm(@PathVariable int id, @PathVariable int userId, Model model) {
        model.addAttribute("listing", listingService.getListingById(id));
        model.addAttribute("user", userService.getUserByUserId(userId));
        return "listing-form";
    }

    /**
     * Save a new listing.
     * @param listing the listing to be saved
     * @return the redirect URL to the listing detail view
     */
    @PostMapping("/save")
    public String createListing(@ModelAttribute("listing") Listing listing){ //userId passed by front end
        listing.setUsername(
                userService.getUserByUserId(
                        listing.getUserId()).
                        getUsername());
        listingService.createListing(listing);
        return "redirect:/listing/listing-id/" + listing.getListingId() + "/user-id/" + listing.getUserId();
    }

    /**
     * Upload an image for a listing.
     * @param image the image file to be uploaded
     * @param listingId the ID of the listing
     * @return the response entity indicating success or failure
     */
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

    /**
     * Update an existing listing.
     * @param listing the listing to be updated
     * @return the redirect URL to the listing detail view
     */
    @PostMapping("/update")
    public String updateListing(Listing listing){
        listingService.updateListing(listing);
        return "redirect:/listing/listing-id/" + listing.getListingId();
    }

    /**
     * Reserve a listing.
     * @param listingId the ID of the listing
     * @param userId the ID of the user
     * @return the redirect URL to the listing detail view
     */
    @GetMapping("/reserve/{listingId}/user-id/{userId}")
    public String reserveListing(@PathVariable int listingId, @PathVariable int userId) {
        Listing listing = listingService.getListingById(listingId);
        listing.setStatus(1);
        listing.setReserveUserId(userId);
        listingService.updateListing(listing);
        return "redirect:/listing/listing-id/"+listingId+"/user-id/"+userId;
    }

    /**
     * Mark a listing as complete.
     * @param listingId the ID of the listing
     * @param userId the ID of the user
     * @return the redirect URL to the listing detail view
     */
    @GetMapping("/complete/{listingId}/user-id/{userId}")
    public String completeListing(@PathVariable int listingId, @PathVariable int userId) {
        Listing listing = listingService.getListingById(listingId);
        listing.setStatus(2);
        listingService.updateListing(listing);
        return "redirect:/listing/listing-id/"+listingId+"/user-id/"+userId;
    }

    /**
     * Delete a listing.
     * @param id the ID of the listing
     * @param userId the ID of the user
     * @return the redirect URL to the user's listings
     */
    @GetMapping("/delete/{id}/user/{userId}/confirmed")
    public String deleteListing(@PathVariable int id, @PathVariable int userId){
        listingService.deleteListing(id);
        return "redirect:/listing/user-id/{userId}";
    }

    /**
     * Confirm deletion of a listing.
     * @param id the ID of the listing
     * @param userId the ID of the user
     * @param model the model to hold attributes
     * @return the listing delete confirmation view
     */
    @GetMapping("/delete/{id}/user/{userId}")
    public String deleteConfirmation(@PathVariable int id, @PathVariable int userId, Model model) {
        model.addAttribute("listing", listingService.getListingById(id));
        model.addAttribute("user", userService.getUserByUserId(userId));
        return "listing-delete-confirmation";
    }

}

