package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.MessageService;
import com.example.service.AccountService;

import java.util.Optional;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(MessageService messageService, AccountService accountService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }
    
    @PostMapping("register")
    public ResponseEntity<Account> userRegistration(@RequestBody Account account) {
        System.out.println("Within the controller. This is the account received: " + account);
        if (accountService.userLogin(account) == null ) {
            accountService.userRegistration(account);
            return ResponseEntity.status(200).body(account);
        }
        else if (accountService.userLogin(account) != null) {
            return ResponseEntity.status(409).body(null);
        }
        else {
            return ResponseEntity.status(400).body(null);
        }
    }

    @PostMapping("login")
    public ResponseEntity<Optional<Account>> userLogin(@RequestBody Account account) {
        if (accountService.userLogin(account) != null) {
            return ResponseEntity.status(200).body(accountService.userLogin(account));
        }
        else {
            return ResponseEntity.status(401).body(null);
        }
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        System.out.println("Within controller. Here's the message received: " + message);
        if(messageService.createMessage(message) == null) {
            return ResponseEntity.status(400).body(null);
        }
        else {
            Message createdMessage = messageService.createMessage(message);
            return ResponseEntity.status(200).body(createdMessage);
        }
    }

    @GetMapping("messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("messages/{message_id}")
    public Message getMessageById(@PathVariable Integer message_id) {
        Message message = messageService.getMessageById(message_id);
        System.out.println("Within the controller. Here's the result " + message);
        if (message != null) {
            return message;
        }
        else {
            return null;
        }
    }

    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer message_id) {
        System.out.println("Within the controller. Here's the message " + messageService.getMessageById(message_id));
        if(messageService.getMessageById(message_id) == null) {
            messageService.deleteMessage(message_id);
            return ResponseEntity.status(200).body(null);
        }
        else {
            return ResponseEntity.status(200).body(1);
        }
    }
}
