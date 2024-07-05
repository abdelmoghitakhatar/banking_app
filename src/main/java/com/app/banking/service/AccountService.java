package com.app.banking.service;

import com.app.banking.domain.Account;
import com.app.banking.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account){

        account = accountRepository.save(account);

        return account;
    }

    public Account getAccountById(Long id){

        return accountRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No Exist With This ID : " + id)
        );
    }

    public Account deposit(Long id, double amount){
        Account account = this.getAccountById(id);

        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    public Account withdraw(Long id, double amount) throws RuntimeException{
        Account account = this.getAccountById(id);

        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient amount");
        }

        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public ResponseEntity<String> delete(Long id){
        accountRepository.deleteById(id);
        return new ResponseEntity<>("account is deleted successfully", HttpStatus.OK);
    }
}
