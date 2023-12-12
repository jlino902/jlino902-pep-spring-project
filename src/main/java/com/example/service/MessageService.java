package com.example.service;

import java.util.List;
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
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) {
        if(!message.getMessage_text().isBlank() && message.getMessage_text().length() < 255 && accountRepository.findById(message.getPosted_by()).isPresent()) {
            return messageRepository.save(message);
        }
        else { return null; }
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int message_id) {
        return messageRepository.getById(message_id);
    }

    public Message deleteMessage (int message_id) {
        Message message = messageRepository.getById(message_id);
        if (message != null) {
            messageRepository.deleteById(message_id);
            return message;
        }
        else { return null; }
    }

    public Message updateMessage (int message_id, Message message) {
        if(messageRepository.getById(message_id) != null && message.getMessage_text().length() < 255 && !message.getMessage_text().isBlank()) {
            return messageRepository.saveAndFlush(message);
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
