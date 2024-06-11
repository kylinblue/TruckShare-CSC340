package com.csc340.truckshare.webapp.services;

import com.csc340.truckshare.webapp.models.*;
import com.csc340.truckshare.webapp.repositories.*;
import com.csc340.truckshare.webapp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ConvService {
    @Autowired
    ConvRepository convRepository; // Repository for accessing conversation data
    @Autowired
    MessageRepository messageRepository; // Repository for accessing message data
    @Autowired
    ListingRepository listingRepository; // Repository for accessing listing data
    @Autowired
    UserRepository userRepository; // Repository for accessing user data

    @Autowired
    UserService userService;
    @Autowired
    ListingService listingService;

    // Method to get conversations by user ID
    public List<Conv> getConvByUserId(int userid){
        return Stream.concat(
                        (convRepository.queryBySourceUserId(userid).stream()),
                        (convRepository.queryByTargetUserId(userid).stream()))
                .toList(); // Query conversations by source user ID
    }

    // Method to get a conversation by source and target user IDs
    public Conv getConvByUserIdPair(int userId1, int userId2){
        // first iteration
        List<Conv> convListOfUser1AsSource = convRepository.queryBySourceUserId(userId1);
        if(convListOfUser1AsSource==null) {
            List<Conv> convListOfUser1AsTarget = convRepository.queryByTargetUserId(userId1);
            if(convListOfUser1AsTarget==null) {
                return null;
            }
            else {
                List<Conv> convListOfUser2AsSource = convRepository.queryBySourceUserId(userId2);
                if(convListOfUser2AsSource==null) {
                    return null;
                }
                else {
                    for (Conv conv : convListOfUser1AsTarget){
                        for (Conv conv2 : convListOfUser2AsSource) {
                            if (conv.getSourceUserId() == conv2.getSourceUserId()){
                                return conv;
                            }
                        }
                    }
                    return null;
                }
            }
        }
        else {
            List<Conv> convListOfUser2AsTarget = convRepository.queryByTargetUserId(userId2);
            if (convListOfUser2AsTarget==null) {
                List<Conv> convListOfUser1AsTarget = convRepository.queryByTargetUserId(userId1);
                if (convListOfUser1AsTarget==null) {
                    return null;
                }
                else{
                    List<Conv> convListOfUser2AsSource = convRepository.queryBySourceUserId(userId2);
                    if (convListOfUser2AsSource==null) {
                        return null;
                    }
                    else{
                        for (Conv conv : convListOfUser1AsTarget) {
                            for (Conv conv2 : convListOfUser2AsSource) {
                                if (conv.getTargetUserId() == conv2.getSourceUserId()) {
                                    return conv;
                                }
                            }
                        }
                        return null;
                    }
                }
            }
            else {
                for (Conv conv : convListOfUser1AsSource) {
                    for (Conv conv2 : convListOfUser2AsTarget) {
                        if (conv.getSourceUserId() == conv2.getTargetUserId()){
                            return conv;
                        }
                    }
                }
                return null;
            }
        }

        /*List<Conv> convListOfUser1AsSource = convRepository.queryBySourceUserId(userId1);
        if (convListOfUser1AsSource!=null) {
            List<Conv> convListOfUser2AsTarget = convRepository.queryByTargetUserId(userId2);
            if (convListOfUser2AsTarget!=null){
                for (Conv conv : convListOfUser1AsSource) {
                    int targetId = conv.getTargetUserId();
                    for (Conv conv2 : convListOfUser2AsTarget) {
                        if (targetId == conv2.getTargetUserId()){
                            return conv2;
                        }
                    }
                }
            }

        }
        else {
            List<Conv> convListOfUser1AsTarget = convRepository.queryByTargetUserId(userId1);
            if (convListOfUser1AsTarget != null) {
                List<Conv> convListOfUser2AsSource = convRepository.queryBySourceUserId(userId2);
                for (Conv conv : convListOfUser1AsTarget) {
                    int sourceId = conv.getSourceUserId();
                    for (Conv conv2 : convListOfUser2AsSource) {
                        if (sourceId == conv2.getSourceUserId()){
                            return conv2;
                        }
                    }
                }
            }
            else return null;
        }*/




        // Ensure sourceUserId is less than targetUserId for consistent query
        /*if (sourceUserId>targetUserId)
        {
            int temp = sourceUserId;
            sourceUserId = targetUserId;
            targetUserId = temp;
        }
        else {
            // Query conversations by source user ID and check target user ID
            List<Conv> convBySource = convRepository.queryBySourceUserId(sourceUserId);
            for (Conv conv : convBySource) {
                if(conv.getTargetUserId() == targetUserId){
                    return conv; // Return conversation if target user ID matches
                }
            }
        }
        return null; // Return null if no conversation is found*/
    }

    // Method to get a conversation by its ID
    public Conv getConvById(int convId) {
        return convRepository.findById(convId).orElse(null); // Find conversation by ID or return null
    }

    // Method to save a conversation
    public void saveConv(Conv conversation) {
        convRepository.save(conversation); // Save the conversation to the repository
    }

    public Conv initConv(int userId, int userId2, int listingId) {
        Conv conv = new Conv();
        Listing listing = listingService.getListingById(listingId);
        conv.setSourceUserId(userId);
        conv.setTargetUserId(userId2);
        conv.setSourceUsername(userService.getUserByUserId(userId).getUsername());
        conv.setTargetUsername(userService.getUserByUserId(userId2).getUsername());
        conv.setListingId(listing.getListingId());
        return conv;
    }

    // Method to delete a conversation by its ID
    //TODO verify functionality
    public void deleteConv(int convId) {
        // Check if message repository is not null
        if (this.messageRepository != null) {
            // Query messages by conversation ID and delete them
            List<Message> msgToDelete = messageRepository.queryMsgByConvId(convId);
            for (Message message : msgToDelete) {
                messageRepository.deleteById(message.getMsgId()); // Delete each message by its ID
            }
        }
        convRepository.deleteById(convId); // Delete the conversation by its ID
    }
}
