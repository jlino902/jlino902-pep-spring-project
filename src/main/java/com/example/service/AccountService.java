package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    public AccountService() { 
        
    }

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account userRegistration(Account account) {
        System.out.println("Within the AccountService. Here's the recieved account: " + account);
        if(!account.getUsername().isBlank() && account.getPassword().length() > 4 && userLogin(account) == null) {
            return accountRepository.save(account);
        }
        else {
            return null;
        }
    }

    public Optional<Account> userLogin(Account account) {
        System.out.println("Within the Account service. This is the account recieved: " + account);
        Account accountExample = new Account(account.getUsername(), account.getPassword());
        Example<Account> example = Example.of(accountExample);
        System.out.println("Within The account service. This is the example: " + example);
        if(accountRepository.exists(example)) {
            return accountRepository.findOne(example);
        }
        else {
            return null;
        }
    }



}
