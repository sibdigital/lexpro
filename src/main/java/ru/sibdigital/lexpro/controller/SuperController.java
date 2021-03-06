package ru.sibdigital.lexpro.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.sibdigital.lexpro.config.ApplicationConstants;
import ru.sibdigital.lexpro.config.CurrentUser;

@Log4j2
@Controller
public class SuperController {

    @Autowired
    protected ApplicationConstants applicationConstants;

    protected CurrentUser getCurrentUser() {
        return (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    protected void addGeneralModelAttributes(Model model) {
        CurrentUser currentUser = getCurrentUser();

        model.addAttribute("user", currentUser.getClsUser());
        model.addAttribute("authorities", currentUser.getAuthorities()); // role: ADMIN, authority: ROLE_ADMIN
        model.addAttribute("application_name", applicationConstants.getApplicationName());
    }

    protected boolean hasCurrentUserAuthority(CurrentUser currentUser, String authorityName) {
        return currentUser
              .getAuthorities()
              .stream()
              .anyMatch(r -> r.getAuthority().equals(authorityName)); // role: ADMIN, authority: ROLE_ADMIN
    }
}
