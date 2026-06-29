package com.exam.controller;

import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller

public class HomeController {

    @GetMapping("/home")

    public String home(Model model) {

        model.addAttribute("serviceStatus", "정상 운영 중");
        return "home";

    }

}