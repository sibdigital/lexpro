package ru.sibdigital.lexpro.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.sibdigital.lexpro.config.ApplicationConstants;
import ru.sibdigital.lexpro.config.CurrentUser;
import ru.sibdigital.lexpro.model.ClsEmployee;
import ru.sibdigital.lexpro.model.ClsUser;
import ru.sibdigital.lexpro.model.RegEmployeeUser;
import ru.sibdigital.lexpro.repository.*;
import ru.sibdigital.lexpro.service.*;

import java.util.List;

@Log4j2
@Controller
public class SuperController {

    @Autowired
    ApplicationConstants applicationConstants;

    @Autowired
    RkkService rkkService;

    @Autowired
    DocRkkRepo docRkkRepo;

    @Autowired
    RegRkkFileRepo regRkkFileRepo;

    @Autowired
    RegUserRoleRepo regUserRoleRepo;

    @Autowired
    RegRolePrivilegeRepo regRolePrivilegeRepo;

    @Autowired
    RolePrivilegeService rolePrivilegeService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RkkFileService rkkFileService;

    @Autowired
    FileService fileService;

    @Autowired
    RegEmployeeUserRepo regEmployeeUserRepo;

    @Autowired
    RegRkkMailingRepo regRkkMailingRepo;

    @Autowired
    RegRkkVisaRepo regRkkVisaRepo;

    @Autowired
    ClsOrganizationRepo clsOrganizationRepo;

    protected CurrentUser getCurrentUser() {
        return (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    protected void addGeneralModelAttributes(Model model) {
        CurrentUser currentUser = getCurrentUser();
        ClsUser user = currentUser.getClsUser();

        model.addAttribute("user", user);
        model.addAttribute("id_user", user.getId());
//        model.addAttribute("authorities", currentUser.getAuthorities()); // role: ADMIN, authority: ROLE_ADMIN
        model.addAttribute("roles", rolePrivilegeService.getUserRoleNames(user));
        model.addAttribute("privileges", rolePrivilegeService.getUserPrivilegeNames(user));
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

    protected ClsEmployee getEmployeeByUser_Id(Long userId) {
        ClsEmployee employee = null;
        RegEmployeeUser employeeUser = regEmployeeUserRepo.findFirstByUser_Id(userId).orElse(null);
        if (employeeUser != null) {
            employee = employeeUser.getEmployee();
        }

        return employee;
    }


}
