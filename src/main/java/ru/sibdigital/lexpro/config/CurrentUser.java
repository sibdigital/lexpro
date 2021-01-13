package ru.sibdigital.lexpro.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ru.sibdigital.lexpro.model.ClsUser;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CurrentUser extends User {

    private ClsUser clsUser;

    public CurrentUser(User user) {
        super(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());
    }

    public ClsUser getClsUser() {
        return clsUser;
    }

    public void setClsUser(ClsUser clsUser) {
        this.clsUser = clsUser;
    }
}
