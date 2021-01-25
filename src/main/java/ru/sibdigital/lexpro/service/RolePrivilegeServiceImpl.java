package ru.sibdigital.lexpro.service;

import org.springframework.stereotype.Service;
import ru.sibdigital.lexpro.model.ClsPrivilege;
import ru.sibdigital.lexpro.model.ClsRole;
import ru.sibdigital.lexpro.model.ClsUser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RolePrivilegeServiceImpl extends SuperServiceImpl implements RolePrivilegeService {

    public List<ClsRole> getUserRoles(ClsUser clsUser) {
        return regUserRoleRepo.findByUser(clsUser)
                .stream()
                .map(regUserRole -> regUserRole.getRole())
                .collect(Collectors.toList());
    }

    public List<ClsPrivilege> getUserPrivileges(ClsUser user) {
        List<ClsRole> userRoles = getUserRoles(user);
        Set<ClsPrivilege> privileges = getRolesPrivileges(userRoles);
        return List.copyOf(privileges);
    }


    public Set<ClsPrivilege> getRolesPrivileges(List<ClsRole> userRoles) {
        Set<ClsPrivilege> privileges = new HashSet<>();
        for (ClsRole role : userRoles) {
            List<ClsPrivilege> rolePrivileges = regRolePrivilegeRepo.findByRole(role)
                    .stream()
                    .map(regRolePrivilege -> regRolePrivilege.getPrivilege())
                    .collect(Collectors.toList());
            privileges.addAll(rolePrivileges);
        }

        return privileges;
    }


    public List<String> getUserRoleNames(ClsUser clsUser) {
        List<ClsRole> userRoles = getUserRoles(clsUser);
        return getRolesName(userRoles);
    }

    public List<String> getUserPrivilegeNames(ClsUser user) {
        List<ClsPrivilege> userPrivileges = getUserPrivileges(user);
        return getPrivilegeNames(userPrivileges);
    }

    private List<String> getRolesName(List<ClsRole> roles) {
        return roles.stream().map(role -> role.getName()).collect(Collectors.toList());
    }

    private List<String> getPrivilegeNames(List<ClsPrivilege> privileges) {
        return privileges.stream().map(privilege -> privilege.getName()).collect(Collectors.toList());
    }

}
