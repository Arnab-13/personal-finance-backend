package com.example.personalFiManager.repository;

import com.example.personalFiManager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}