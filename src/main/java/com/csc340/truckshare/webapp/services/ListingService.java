package com.csc340.truckshare.webapp.services;

import com.csc340.truckshare.webapp.models.Listing;
import com.csc340.truckshare.webapp.repositories.ConvRepository;
import com.csc340.truckshare.webapp.repositories.ListingRepository;
import com.csc340.truckshare.webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingService {
    @Autowired
    ListingRepository listingRepository;
    UserRepository userRepository;
    ConvRepository convRepository;

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public List<Listing> queryByUserId(int userId){
        return listingRepository.queryByUserId(userId);
    }

    public void createListing(Listing listing) {
        listingRepository.save(listing);
    }

    public void updateListing(Listing listing) {
        listingRepository.save(listing);
    }

    public void deleteListing(int id) {
        listingRepository.deleteById(id);
    }

}
