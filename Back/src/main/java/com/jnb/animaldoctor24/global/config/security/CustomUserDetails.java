package com.jnb.animaldoctor24.global.config.security;

import com.jnb.animaldoctor24.domain.member.domain.Role;
import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {


    @NonNull
    private final String email;

    @Nullable
    private final String password;

    @NonNull
    private final Role role;

    @NonNull
    private final String activeYn;

    public CustomUserDetails(String email, Role role) {
        this.email = email;
        this.password = null;
        this.role = role;
        this.activeYn = "Y";
    }

    public CustomUserDetails(String email, String password, Role role, String activeYn) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.activeYn = activeYn;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> list = new ArrayList<>();
        list.add(() -> String.format("ROLE_%s", role.name()));
        return list;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        //return email;
        return email;
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
        return "Y".equals(activeYn);
    }
}
