package com.example.clients_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.clients_management_system.models.Clients;

public interface ClientRepository extends JpaRepository<Clients, Integer> {
    public Clients findByEmail(String email);
    
}
