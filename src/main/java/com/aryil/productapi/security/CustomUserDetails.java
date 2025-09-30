package com.aryil.productapi.security;


import com.aryil.productapi.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {
    private final User user;
    public CustomUserDetails(User user) { this.user = user; }

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> auths = new HashSet<>();
        user.getRoles().forEach(role -> {
            auths.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            role.getPermissions().forEach(p ->
                    auths.add(new SimpleGrantedAuthority(p.getName()))
            );
        });
        return auths;
    }

    @Override public String getPassword() { return user.getPassword(); }
    @Override public String getUsername() { return user.getUsername(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    public User getDomainUser() { return user; }
}