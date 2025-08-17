package com.example.personalFiManager.repository;

import com.example.personalFiManager.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}