package com.example.clients_management_system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.clients_management_system.repositories.ClientRepository;

@Controller
public class HomeController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/")
    public String home(Model model) {
        long clientCount = clientRepository.count();
        long activeCount = clientRepository.findByStatus("active", org.springframework.data.domain.Sort.by("id")).size();
        long inactiveCount = clientRepository.findByStatus("inactive", org.springframework.data.domain.Sort.by("id")).size();
        model.addAttribute("clientCount", clientCount);
        model.addAttribute("activeCount", activeCount);
        model.addAttribute("inactiveCount", inactiveCount);
        return "index";
    }
}
