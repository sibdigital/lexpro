package ru.sibdigital.lexpro.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sibdigital.lexpro.config.CurrentUser;

@Log4j2
@Controller
public class MainController extends SuperController {

    @GetMapping("/")
    public String index() {
        CurrentUser currentUser = getCurrentUser();

        if (hasCurrentUserAuthority(currentUser, "ROLE_ADMIN")) {
            return "redirect:/admin";
        }

        return "redirect:/cabinet";
    }

    @GetMapping("/cabinet")
    public String cabinet(Model model) {
        addGeneralModelAttributes(model);
        return "user";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        addGeneralModelAttributes(model);
        return "admin";
    }
}
