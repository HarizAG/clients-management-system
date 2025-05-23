package com.example.clients_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.clients_management_system.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
} 