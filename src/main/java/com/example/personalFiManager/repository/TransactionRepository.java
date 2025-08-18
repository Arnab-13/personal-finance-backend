package com.example.personalFiManager.repository;

import com.example.personalFiManager.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.personalFiManager.model.User;
import java.util.List;



public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
}