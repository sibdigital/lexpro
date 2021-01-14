package ru.sibdigital.lexpro.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class LoginController extends SuperController {

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("application_name", applicationConstants.getApplicationName());
        return "login";
    }
}