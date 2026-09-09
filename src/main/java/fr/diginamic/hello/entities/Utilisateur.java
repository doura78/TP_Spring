package fr.diginamic.hello.entities;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Utilisateur implements UserDetails {

    private String username;
    private String password;
    private List<Role> roles = new ArrayList<>();

    public Utilisateur(String password, String username, String roleName) {
        this.password = password;
        this.username = username;
        this.roles.add(new Role(roleName));
    }

    public Utilisateur(String admin,  String encode, Role roles) {
        this.username = admin;
        this.password = encode;
        this.roles.add(roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
