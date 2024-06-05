package com.csc340.truckshare.webapp.services;

import com.csc340.truckshare.webapp.models.Listing;
import com.csc340.truckshare.webapp.models.User;
import com.csc340.truckshare.webapp.repositories.ConvRepository;
import com.csc340.truckshare.webapp.repositories.ListingRepository;
import com.csc340.truckshare.webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ListingRepository listingRepository;
    @Autowired
    ConvRepository convRepository;

    public User getUserByUserName(String username, String password) {
        User user = userRepository.findByUsername(username);
        // Need to implement password hash, currently plain text
        if (user.getUserPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void createUser(User user) {
        /*if (getUserByUserName(user.getUsername(), user.getUserPassword())==null)
        {

        }
        else return -1;*/
        userRepository.save(user);
    }

    public User getUserByUserId(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public void deleteUserById(int userId) {
        if (this.listingRepository != null) {
            List<Listing> listingToDelete = listingRepository.queryByUserId(userId);
            for (Listing listing : listingToDelete) {
                listingRepository.deleteById(listing.getListingId());
            }
        }
        userRepository.deleteById(userId);
    }
}
