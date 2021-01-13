package ru.sibdigital.lexpro.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sibdigital.lexpro.config.ApplicationConstants;
import ru.sibdigital.lexpro.config.CurrentUser;
import ru.sibdigital.lexpro.model.*;

@Log4j2
@Controller
public class MainController {

    @Autowired
    private ApplicationConstants applicationConstants;

    @GetMapping("/")
    public String index() {
        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin";
        }

        return "redirect:/cabinet";
    }

    @GetMapping("/cabinet")
    public String cabinet(Model model) {
        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ClsUser clsUser = currentUser.getClsUser();
        model.addAttribute("user_lastname", clsUser.getLastname());
        model.addAttribute("user_firstname", clsUser.getFirstname());
        model.addAttribute("application_name", applicationConstants.getApplicationName());
        return "user";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ClsUser clsUser = currentUser.getClsUser();
        model.addAttribute("user_lastname", clsUser.getLastname());
        model.addAttribute("user_firstname", clsUser.getFirstname());
        model.addAttribute("application_name", applicationConstants.getApplicationName());
        return "admin";
    }
}
