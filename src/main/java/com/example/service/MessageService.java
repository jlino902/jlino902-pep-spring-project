package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    public MessageService() {
    }

    @Autowired
    public MessageService(AccountRepository accountRepository, MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) {
        if(!message.getMessage_text().isBlank() && message.getMessage_text().length() < 255  && accountRepository.existsById(message.getPosted_by())) {
            return messageRepository.save(message);
        }
        else { return null; }
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Integer message_id) {
        System.out.println("Within the message service. Here's the id: " + message_id + " and the message: " + messageRepository.findById(message_id) );
        Optional<Message> message = messageRepository.findById(message_id);
        if (message != null) {
            return message;
        }
        else {
            return null;
        }
    }

    public Optional<Message> deleteMessage (int message_id) {
        Optional<Message> message = messageRepository.findById(message_id);
        System.out.println("Within the service. Here's the message" + message);
        if (message != null) {
            messageRepository.deleteById(message_id);
            return message;
        }
        else { return null; }
    }

    public Message updateMessage (int message_id, Message message) {
        Message foundMessage = messageRepository.getById(message_id);
        
        if(foundMessage != null && message.getMessage_text().length() < 255 && !message.getMessage_text().isBlank()) {
            foundMessage.setMessage_text(message.getMessage_text());
            Message updatedMessage = messageRepository.saveAndFlush(foundMessage);
            return updatedMessage;
        }
        else { return null; }
    }

    public List<Message> getAllMessagesByUser(int account_id) {
        Message messageExample = new Message();
        messageExample.setPosted_by(account_id);
        Example<Message> example = Example.of(messageExample);
        return messageRepository.findAll(example);
    }

}
