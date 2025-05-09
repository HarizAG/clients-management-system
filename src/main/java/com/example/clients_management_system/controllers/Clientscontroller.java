package com.example.clients_management_system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clients_management_system.models.Clients;
import com.example.clients_management_system.models.ClientsDto;
import com.example.clients_management_system.repositories.ClientRepository;

@Controller
@RequestMapping("/clients")
public class Clientscontroller {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping({"", "/"})
    public String getClients(Model model, @RequestParam(required = false) String status) {
        var clients = status != null && !status.isEmpty()
            ? clientRepository.findByStatus(status, Sort.by(Sort.Direction.ASC, "id"))
            : clientRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        
        // Count clients for each status
        long totalCount = clientRepository.count();
        long activeCount = clientRepository.findByStatus("active", Sort.by(Sort.Direction.ASC, "id")).size();
        long inactiveCount = clientRepository.findByStatus("inactive", Sort.by(Sort.Direction.ASC, "id")).size();
        long leadCount = clientRepository.findByStatus("lead", Sort.by(Sort.Direction.ASC, "id")).size();
        long occasionalCount = clientRepository.findByStatus("occasional", Sort.by(Sort.Direction.ASC, "id")).size();
        long permanentCount = clientRepository.findByStatus("permanent", Sort.by(Sort.Direction.ASC, "id")).size();

        // Add counts to model
        model.addAttribute("clients", clients);
        model.addAttribute("currentStatus", status);
        model.addAttribute("clientCount", totalCount);
        model.addAttribute("activeCount", activeCount);
        model.addAttribute("inactiveCount", inactiveCount);
        model.addAttribute("leadCount", leadCount);
        model.addAttribute("occasionalCount", occasionalCount);
        model.addAttribute("permanentCount", permanentCount);

        return "clients/index";
    }

    @GetMapping("/create")
    public String createClient(Model model) {
        ClientsDto clientDto = new ClientsDto();
        model.addAttribute("clientDto", clientDto);
        return "clients/create";
    }

    @PostMapping("/create")
    public String createClient(@ModelAttribute ClientsDto clientDto) {
        Clients client = new Clients();
        client.setFirstname(clientDto.getFirstname());
        client.setLastname(clientDto.getLastname());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());
        client.setAddress(clientDto.getAddress());
        client.setStatus(clientDto.getStatus());
        clientRepository.save(client);

        return "redirect:/clients";
    }

    @GetMapping("/edit")
    public String editClient(Model model, @RequestParam int id) {
        Clients client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return "redirect:/clients";
        }

        ClientsDto clientDto = new ClientsDto();
        clientDto.setFirstname(client.getFirstname());
        clientDto.setLastname(client.getLastname());
        clientDto.setEmail(client.getEmail());
        clientDto.setPhone(client.getPhone());
        clientDto.setAddress(client.getAddress());
        clientDto.setStatus(client.getStatus());

        model.addAttribute("clientDto", clientDto);
        model.addAttribute("clientId", id); // Pass the client ID to the view

        return "clients/edit";
    }

    @PostMapping("/update")
    public String updateClient(@RequestParam Integer id, @ModelAttribute ClientsDto clientDto) {
        Clients client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return "redirect:/clients";
        }

        client.setFirstname(clientDto.getFirstname());
        client.setLastname(clientDto.getLastname());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());
        client.setAddress(clientDto.getAddress());
        client.setStatus(clientDto.getStatus());

        clientRepository.save(client);

        return "redirect:/clients";
    }

    @PostMapping("/delete")
    public String deleteClient(@RequestParam Integer id) {
        Clients client = clientRepository.findById(id).orElse(null);
        if (client != null) {
            clientRepository.delete(client);
        }
        return "redirect:/clients";
    }
}