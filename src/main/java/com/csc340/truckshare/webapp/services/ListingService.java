package com.csc340.truckshare.webapp.services;

import com.csc340.truckshare.webapp.models.Listing;
import com.csc340.truckshare.webapp.models.User;
import com.csc340.truckshare.webapp.repositories.ConvRepository;
import com.csc340.truckshare.webapp.repositories.ListingRepository;
import com.csc340.truckshare.webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Marks this class as a Spring Service component
public class ListingService {

    @Autowired // Automatically injects the ListingRepository dependency
    ListingRepository listingRepository;

    // UserRepository and ConvRepository are declared
    UserRepository userRepository;
    ConvRepository convRepository;

    UserService userService;

    /**
     * Fetches all listings from the repository.
     * @return a list of all listings.
     */
    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    /**
     * Fetches a listing by its ID.
     * @param listingId the ID of the listing to retrieve.
     * @return the Listing object if found, otherwise null.
     */
    public Listing getListingById(int listingId) {
        return listingRepository.findById(listingId).orElse(null);
    }

    public List<Listing> queryByUserId(int userId){
        return listingRepository.queryByUserId(userId);
    }

    /**
     * Creates a new listing.
     * @param listing the Listing object to be created.
     */
    public void createListing(Listing listing) {
        listing.setConvId(0);
        listingRepository.save(listing);
    }

    /**
     * Updates an existing listing.
     * @param listing the Listing object with updated information.
     */
    public void updateListing(Listing listing) {
        listingRepository.save(listing); // save() can be used for both creating and updating
    }

    /**
     * Deletes a listing by its ID.
     * @param id the ID of the listing to be deleted.
     */
    public void deleteListing(int id) {
        listingRepository.deleteById(id);
    }
}
