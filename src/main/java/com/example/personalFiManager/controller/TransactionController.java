package com.example.personalFiManager.controller;

import com.example.personalFiManager.dto.TransactionDto;
import com.example.personalFiManager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllUserTransactions() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(transactionService.getAllTransactionsForUser(username));
    }

    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(transactionService.createTransaction(transactionDto, username));
    }
}
