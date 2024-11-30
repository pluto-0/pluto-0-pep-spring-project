package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.*;
import com.example.repository.*;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;

    public Account saveAccount(Account account) {
        if (account.getPassword().length() < 4 || accountRepository.existsAccountByUsername(account.getUsername())) {
            return null;
        }
        Account ans = accountRepository.save(account);
        return ans;
    }
 
    public Account login(Account account) {
        if (!accountRepository.existsAccountByUsername(account.getUsername())) {
            return null;
        }
        Account dbAccount = accountRepository.findAccountByUsername(account.getUsername());
        if (!dbAccount.getPassword().equals(account.getPassword())) {
            return null;
        }
        return dbAccount;
    }

    public boolean accountExistsById(Integer id) {
        return accountRepository.existsAccountByAccountId(id);
    }
}
