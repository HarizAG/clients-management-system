package com.example.clients_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import java.util.List;
import com.example.clients_management_system.models.Clients;

public interface ClientRepository extends JpaRepository<Clients, Integer> {
    public Clients findByEmail(String email);
    public List<Clients> findByStatus(String status, Sort sort);
}
