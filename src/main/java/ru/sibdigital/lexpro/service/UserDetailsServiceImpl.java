package ru.sibdigital.lexpro.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sibdigital.lexpro.config.CurrentUser;
import ru.sibdigital.lexpro.model.*;

@Service
public class UserDetailsServiceImpl extends SuperServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        ClsUser clsUser = clsUserRepo.findByLogin(login);

        User.UserBuilder builder = null;
        if (clsUser != null) {
            builder = User.withUsername(login);
//            builder.password(passwordEncoder.encode(clsUser.getPassword()));
            builder.password(clsUser.getPassword());
//            String[] roleNames = rolePrivilegeService.getUserRoleNames(clsUser).toArray(String[]::new);
//            builder.roles(roleNames);
            builder.authorities(rolePrivilegeService.getPrivileges(clsUser));

        } else {
            throw new UsernameNotFoundException("User no found.");
        }

        CurrentUser currentUser = new CurrentUser((User) builder.build());
        currentUser.setClsUser(clsUser);

        return currentUser;
    }

}
