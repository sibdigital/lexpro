package ru.sibdigital.lexpro.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.sibdigital.lexpro.config.ApplicationConstants;
import ru.sibdigital.lexpro.config.CurrentUser;
import ru.sibdigital.lexpro.repository.DocRkkRepo;
import ru.sibdigital.lexpro.repository.RegDocRkkFileRepo;
import ru.sibdigital.lexpro.repository.RegRolePrivilegeRepo;
import ru.sibdigital.lexpro.repository.RegUserRoleRepo;
import ru.sibdigital.lexpro.service.RkkService;
import ru.sibdigital.lexpro.service.UserDetailsServiceImpl;

@Log4j2
@Controller
public class SuperController {

    @Autowired
    protected ApplicationConstants applicationConstants;

    @Autowired
    protected RkkService rkkService;

    @Autowired
    protected DocRkkRepo docRkkRepo;

    @Autowired
    protected RegDocRkkFileRepo regDocRkkFileRepo;

    @Autowired
    protected RegUserRoleRepo regUserRoleRepo;

    @Autowired
    protected RegRolePrivilegeRepo regRolePrivilegeRepo;

    @Autowired
    protected UserDetailsServiceImpl userDetailsServiceImpl;

    protected CurrentUser getCurrentUser() {
        return (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    protected void addGeneralModelAttributes(Model model) {
        CurrentUser currentUser = getCurrentUser();

        model.addAttribute("user", currentUser.getClsUser());
//        model.addAttribute("authorities", currentUser.getAuthorities()); // role: ADMIN, authority: ROLE_ADMIN
        model.addAttribute("roles", userDetailsServiceImpl.getUserRoleNames(currentUser.getClsUser()));
        model.addAttribute("privileges", userDetailsServiceImpl.getUserPrivilegeNames(currentUser.getClsUser()));
        model.addAttribute("application_name", applicationConstants.getApplicationName());
    }

    protected boolean hasCurrentUserGotAuthority(CurrentUser currentUser, String authorityName) {
        return currentUser
              .getAuthorities()
              .stream()
              .anyMatch(r -> r.getAuthority().equals(authorityName)); // role: ADMIN, authority: ROLE_ADMIN
    }

    protected boolean hasCurrentUserGotRole(CurrentUser currentUser, String roleName) {
        return regUserRoleRepo.findByUser(currentUser.getClsUser())
                .stream()
                .anyMatch(regUserRole -> regUserRole.getRole().getName().equals(roleName)); // role: ADMIN, authority: ROLE_ADMIN
    }


}
