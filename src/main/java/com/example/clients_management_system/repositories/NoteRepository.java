package com.example.clients_management_system.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.clients_management_system.models.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByClientId(Long clientId);
    List<Note> findByClientId(int clientId);
}
