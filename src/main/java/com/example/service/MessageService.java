package com.example.service;

import java.util.*;
import static java.lang.System.out;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.service.*;
import com.example.entity.*;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;

    @Autowired AccountService accountService;

    public Message postMessage(Message message) {
        if (message.getMessageText().length() == 0 || message.getMessageText().length() >= 255 || !accountService.accountExistsById(message.getPostedBy())) {
            return null;
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent()) {
            return message.get();
        }
        return null;
    }

    public Integer deleteMessageById(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return null;
    }

    public Integer updateMessageById(Integer messageId, String newMessage) {
        System.out.println(newMessage);
        System.out.println(newMessage.length());
        if (!messageRepository.existsById(messageId) || newMessage.length() > 255 || newMessage.length() == 0) {
            return null;
        }
        Message messageToUpdate = messageRepository.findById(messageId).get();
        messageToUpdate.setMessageText(newMessage);
        messageRepository.save(messageToUpdate);
        return 1;
    }

    public List<Message> getAllMessagesByAccountId(Integer accountId) {
        return messageRepository.getAllMessageByPostedBy(accountId);
    }
}
