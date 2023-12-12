package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    public AccountService() { }

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account userRegistration(Account account) {
        if(!account.getUsername().isBlank() && account.getPassword().length() > 4 && accountRepository.getById(account.getAccount_id()) == null) {
            return accountRepository.save(account);
        }
        else {
            return null;
        }
    }

    public Account userLogin(Account account) {
        Account accountExample = new Account();
        accountExample.setUsername(account.getUsername());
        accountExample.setPassword(account.getPassword());
        Example<Account> example = Example.of(accountExample);
        if(accountRepository.exists(example)) {
            return account;
        }
        else {
            return null;
        }
    }



}
