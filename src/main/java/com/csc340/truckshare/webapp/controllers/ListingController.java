package com.csc340.truckshare.webapp.controllers;

import com.csc340.truckshare.webapp.models.Listing;
import com.csc340.truckshare.webapp.services.ConvService;
import com.csc340.truckshare.webapp.services.ListingService;
import com.csc340.truckshare.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public List<Listing> getAllListings(){
        return listingService.getAllListings();
    }
    /* public String getAllListings(Model model){
    model.addAttribute("allListings", listingService.getAllListings());
    return "all-Listings";
    }
     */

    @GetMapping("/userid/{userid}")
    public List<Listing> findListingByUserId(@PathVariable int userId){
        return listingService.queryByUserId(userId);
    }
    /* public String findListingByUserId(){
        return "find-Listing-By-User-Id";
    }*/

    @PostMapping("/create")
    public String createListing(Listing listing, int userId){
        listing.setUserId(userId);
        listingService.createListing(listing);
        return "listing-created";
    }
    /*public RedirectView createListing(Listing listing, RedirectAttributes attributes) {
        listingService.createListing(listing);
        attributes.addFlashAttribute("message", "Listing created successfully!");
        return new RedirectView("/webapp/user/userid/");
                                                            //fix address!!
    }*/

    @PostMapping("/update")
    public String updateListing(Listing listing){
        listingService.updateListing(listing);
        return "listing-updated";
    }
    /*public RedirectView updateListing(@RequestParam("listingId") int id, Listing listing, RedirectAttributes attributes) {
        listingService.updateListing(listing);
        attributes.addFlashAttribute("message", "Listing updated successfully!");
        return new RedirectView("/webapp/user/userid/");
                                                             //fix address!!
    }*/

    @PostMapping("/delete")
    public String deleteListing(int id){
        listingService.deleteListing(id);
        return "listing-deleted";
    }
    /*public RedirectView deleteListing(@RequestParam("listingId") int id, RedirectAttributes attributes) {
        listingService.deleteListing(id);
        attributes.addFlashAttribute("message", "Listing deleted successfully!");
        return new RedirectView("/webapp/user/userid/");
    }*/
}

