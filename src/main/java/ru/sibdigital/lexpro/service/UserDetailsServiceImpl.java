package ru.sibdigital.lexpro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sibdigital.lexpro.config.CurrentUser;
import ru.sibdigital.lexpro.model.ClsUser;
import ru.sibdigital.lexpro.model.RegUserRole;
import ru.sibdigital.lexpro.repository.ClsUserRepo;
import ru.sibdigital.lexpro.repository.RegUserRoleRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ClsUserRepo clsUserRepo;

    @Autowired
    private RegUserRoleRepo regUserRoleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        ClsUser clsUser = clsUserRepo.findByLogin(login);

        User.UserBuilder builder = null;
        if (clsUser != null) {
            builder = User.withUsername(login);
//            builder.password(passwordEncoder.encode(clsUser.getPassword()));
            builder.password(clsUser.getPassword());
            builder.roles(getUserRoles(clsUser));
        } else {
            throw new UsernameNotFoundException("User no found.");
        }

        CurrentUser currentUser = new CurrentUser((User) builder.build());
        currentUser.setClsUser(clsUser);

        return currentUser;
    }

    private String[] getUserRoles(ClsUser user) {
        List<RegUserRole> userRoles = regUserRoleRepo.findByUser(user);
        List<String> roleNames = userRoles.stream().map(regUserRole -> regUserRole.getRole().getName()).collect(Collectors.toList());
        return roleNames.toArray(String[]::new);
    }
}
