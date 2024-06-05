package com.csc340.truckshare.webapp.services;

import com.csc340.truckshare.webapp.models.Conv;
import com.csc340.truckshare.webapp.models.Message;
import com.csc340.truckshare.webapp.repositories.ConvRepository;
import com.csc340.truckshare.webapp.repositories.ListingRepository;
import com.csc340.truckshare.webapp.repositories.MessageRepository;
import com.csc340.truckshare.webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConvService {
    @Autowired
    ConvRepository convRepository;
    MessageRepository messageRepository;
    ListingRepository listingRepository;
    UserRepository userRepository;

    public List<Conv> getConvByUserId(int userId){
        return convRepository.queryByUserId(userId);
    }

    public Conv getConvById(int convId) {
        return convRepository.findById(convId).orElse(null);
    }

    public void saveConv(Conv conversation) {
        convRepository.save(conversation);
    }

    public void deleteConv(int convId) {
        if (this.messageRepository != null) {
            List<Message> msgToDelete = messageRepository.queryMsgByConvId(convId);
            for (Message message : msgToDelete) {
                messageRepository.deleteById(message.getMsgId());
            }
        }
        convRepository.deleteById(convId);
    }
}
