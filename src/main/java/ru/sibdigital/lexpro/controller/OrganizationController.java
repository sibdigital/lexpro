package ru.sibdigital.lexpro.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
public class OrganizationController extends SuperController {

    @GetMapping("/organization_tree")
    public @ResponseBody
    String getOrganizationTree() {
        String s = clsOrganizationRepo.getOrganizationTree();
        return s;
    }
}
