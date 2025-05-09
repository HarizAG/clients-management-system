package com.example.clients_management_system.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.clients_management_system.models.Clients;
import com.example.clients_management_system.models.Note;
import com.example.clients_management_system.repositories.ClientRepository;
import com.example.clients_management_system.repositories.NoteRepository;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private ClientRepository clientRepository;

    // For form submissions (not AJAX)
    @PostMapping("/add")
    public String addNote(@RequestParam int clientId, @RequestParam String content) {
        Clients client = clientRepository.findById(clientId).orElseThrow();
        Note note = new Note();
        note.setContent(content);
        note.setClient(client);
        noteRepository.save(note);
        return "redirect:/clients";
    }

    // For AJAX/JSON requests
    @PostMapping("/addNote")
    @ResponseBody
    public Map<String, Object> addNoteAjax(@RequestBody Map<String, String> payload) {
        Map<String, Object> response = new HashMap<>();
        try {
            int clientId = Integer.parseInt(payload.get("clientId"));
            String noteContent = payload.get("noteContent");
            Clients client = clientRepository.findById(clientId).orElseThrow();
            Note note = new Note();
            note.setContent(noteContent);
            note.setClient(client);
            noteRepository.save(note);
            response.put("success", true);
        } catch (NumberFormatException e) {
            response.put("success", false);
        }
        return response;
    }

    // View notes for a specific client
    @GetMapping("/client/{id}")
    public String viewNotesForClient(@PathVariable int id, Model model) {
        Clients client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return "redirect:/clients";
        }
        List<Note> notes = noteRepository.findByClientId(id);
        model.addAttribute("client", client);
        model.addAttribute("notes", notes);
        return "notes/view";
    }
}