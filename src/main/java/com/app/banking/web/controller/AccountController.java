package com.app.banking.web.controller;

import com.app.banking.domain.Account;
import com.app.banking.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Account account ) throws RuntimeException {

        if(account.getId() != null){
            throw new RuntimeException("new account may not have an id");
        }
        return new ResponseEntity<>(accountService.createAccount(account), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable(name = "id") Long id){

        if(id == null){
            throw new RuntimeException("existed account may have an id");
        }
        return new ResponseEntity<>(accountService.getAccountById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable Long id, @RequestBody Map<String, Double> request) throws RuntimeException {

        if(id == null){
            throw new RuntimeException("existed account may have an id");
        }
        if(request.get("amount") < 0){
            throw new RuntimeException("you can't depose a negative value");
        }
        return new ResponseEntity<>(accountService.deposit(id, request.get("amount")), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request) throws RuntimeException {

        if(id == null){
            throw new RuntimeException("existed account may have an id");
        }
        if(request.get("amount") < 0){
            throw new RuntimeException("you can't depose a negative value");
        }
        return new ResponseEntity<>(accountService.withdraw(id, request.get("amount")), HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAllAccounts(){
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAccount(@PathVariable Long id){
        accountService.delete(id);
    }
}
