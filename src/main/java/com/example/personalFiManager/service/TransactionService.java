package com.example.personalFiManager.service;

import com.example.personalFiManager.dto.TransactionDto;
import com.example.personalFiManager.model.Category;
import com.example.personalFiManager.model.Transaction;
import com.example.personalFiManager.model.User;
import com.example.personalFiManager.repository.CategoryRepository;
import com.example.personalFiManager.repository.TransactionRepository;
import com.example.personalFiManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Method to get all transactions for a specific user
    public List<TransactionDto> getAllTransactionsForUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return transactionRepository.findByUser(user)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Method to create a new transaction
    public TransactionDto createTransaction(TransactionDto transactionDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Category category = categoryRepository.findById(transactionDto.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));

        Transaction transaction = new Transaction();
        transaction.setDescription(transactionDto.getDescription());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDate(transactionDto.getDate());
        transaction.setType(transactionDto.getType());
        transaction.setCategory(category);
        transaction.setUser(user); // Link the transaction to the user!

        Transaction savedTransaction = transactionRepository.save(transaction);
        return convertToDto(savedTransaction);
    }

    // Helper method to convert an Entity to a DTO
    private TransactionDto convertToDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setDescription(transaction.getDescription());
        dto.setAmount(transaction.getAmount());
        dto.setDate(transaction.getDate());
        dto.setType(transaction.getType());
        if (transaction.getCategory() != null) {
            dto.setCategoryId(transaction.getCategory().getId());
            dto.setCategoryName(transaction.getCategory().getName());
        }
        return dto;
    }

    // You would also add methods here for updating and deleting transactions
}

