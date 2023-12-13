package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
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
        Optional<Message> foundMessage = messageRepository.findById(message_id);
        
        if(!foundMessage.isEmpty() && message.getMessage_text().length() < 255 && !message.getMessage_text().isBlank()) {
            Message plainMessage = messageRepository.getById(message_id);
            plainMessage.setMessage_text(message.getMessage_text());
            return messageRepository.save(plainMessage);
        }
        else { return null; }
    }

    public List<Message> getAllMessagesByUser(int account_id) {
        Message messageExample = new Message(null, account_id, null, null);
        Example<Message> example = Example.of(messageExample);
        return messageRepository.findAll(example);
    }

}
