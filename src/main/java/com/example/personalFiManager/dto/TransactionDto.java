package com.example.personalFiManager.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionDto {
    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private String type; // "INCOME" or "EXPENSE"
    private Long categoryId; // We only need the ID of the category
    private String categoryName; // We can also send back the name for display
}
