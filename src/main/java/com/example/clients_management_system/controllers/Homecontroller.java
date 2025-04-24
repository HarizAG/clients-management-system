package com.example.clients_management_system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Homecontroller {
    // This method will handle the requests to the root URL ("/")
    @GetMapping("/")
    public String home() {
        // Return the name of the view to be rendered (home.html)
        return "index";
    }
}
