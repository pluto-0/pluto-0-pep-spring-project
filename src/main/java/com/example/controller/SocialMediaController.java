package com.example.controller;

import java.util.*;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.*;
import com.example.service.*;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> register(@RequestBody Account new_account) {
        Account registeredAccount = accountService.saveAccount(new_account);
        if (registeredAccount != null) {
            return ResponseEntity.status(200).body(registeredAccount);
        }
        if (new_account.getPassword().length() < 4) {
            return ResponseEntity.status(400).body(registeredAccount);
        }
        return ResponseEntity.status(409).body(registeredAccount);
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> login(@RequestBody Account account) {
        Account loggedInAccount = accountService.login(account);
        if (loggedInAccount == null) {
            return ResponseEntity.status(401).body(account);
        }
        return ResponseEntity.status(200).body(loggedInAccount);
    }

    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> postMessage(@RequestBody Message message) {
        Message postedMessage = messageService.postMessage(message);
        if (postedMessage == null) {
            return ResponseEntity.status(400).body(message);
        }
        return ResponseEntity.status(200).body(postedMessage);
    }

    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Message> getMessageById(@PathVariable Integer message_id) {
        return ResponseEntity.status(200).body(messageService.getMessageById(message_id));
    }

    @DeleteMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Integer> deleteMessageById(@PathVariable Integer message_id) {
        return ResponseEntity.status(200).body(messageService.deleteMessageById(message_id));
    }

    @PatchMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Integer> updateMessageById(@PathVariable Integer message_id, @RequestBody Message messageText) {
        if (messageService.updateMessageById(message_id, messageText.getMessageText()) == null) {
            return ResponseEntity.status(400).body(0);
        }
        return ResponseEntity.status(200).body(1);
    }

    @GetMapping("/accounts/{account_id}/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable Integer account_id) {
        return ResponseEntity.status(200).body(messageService.getAllMessagesByAccountId(account_id));
    }
}
