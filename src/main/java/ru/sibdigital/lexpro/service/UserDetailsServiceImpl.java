package ru.sibdigital.lexpro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sibdigital.lexpro.config.CurrentUser;
import ru.sibdigital.lexpro.model.*;
import ru.sibdigital.lexpro.repository.ClsUserRepo;
import ru.sibdigital.lexpro.repository.RegRolePrivilegeRepo;
import ru.sibdigital.lexpro.repository.RegUserRoleRepo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ClsUserRepo clsUserRepo;

    @Autowired
    private RegUserRoleRepo regUserRoleRepo;

    @Autowired
    private RegRolePrivilegeRepo regRolePrivilegeRepo;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        ClsUser clsUser = clsUserRepo.findByLogin(login);

        User.UserBuilder builder = null;
        if (clsUser != null) {
            builder = User.withUsername(login);
//            builder.password(passwordEncoder.encode(clsUser.getPassword()));
            builder.password(clsUser.getPassword());
//            builder.roles(getUserRoles(clsUser));
            builder.roles(getUserRoleNames(clsUser).toArray(String[]::new));

        } else {
            throw new UsernameNotFoundException("User no found.");
        }

        CurrentUser currentUser = new CurrentUser((User) builder.build());
        currentUser.setClsUser(clsUser);

        return currentUser;
    }


//    private String[] getUserRoles(ClsUser user) {
//        List<RegUserRole> userRoles = regUserRoleRepo.findByUser(user);
//        List<String> roleNames = userRoles.stream()
//                                .map(regUserRole -> regUserRole.getRole().getName())
//                                .collect(Collectors.toList());
//        return roleNames.toArray(String[]::new);
//    }


    public List<String> getUserRoleNames(ClsUser clsUser) {
        List<ClsRole> userRoles = getUserRoles(clsUser);
        return getRolesName(userRoles);
    }


    private List<String> getRolesName(List<ClsRole> roles) {
        return roles.stream().map(role -> role.getName()).collect(Collectors.toList());
    }

    public List<ClsRole> getUserRoles(ClsUser clsUser) {
        return regUserRoleRepo.findByUser(clsUser)
                .stream()
                .map(regUserRole -> regUserRole.getRole())
                .collect(Collectors.toList());
    }

    public List<String> getUserPrivilegeNames(ClsUser user) {
        List<ClsPrivilege> userPrivileges = getUserPrivileges(user);
        return getPrivilegeNames(userPrivileges);
    }

    private List<String> getPrivilegeNames(List<ClsPrivilege> privileges) {
        return privileges.stream().map(privilege -> privilege.getName()).collect(Collectors.toList());
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
}
