package kr.itycoon.plutoid.global.config.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.itycoon.plutoid.biz.common.model.MemberRoleEnum;
import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {

    @NonNull
    private final String memberId;

    @NonNull
    private final String email;

    @Nullable
    private final String password;

    @NonNull
    private final MemberRoleEnum memberRole;

    @NonNull
    private final String activeYn;

    public CustomUserDetails(String memberId, String email, MemberRoleEnum memberRole) {
        this.memberId = memberId;
        this.email = email;
        this.password = null;
        this.memberRole = memberRole;
        this.activeYn = "Y";
    }

    public CustomUserDetails(String memberId, String email, String password, MemberRoleEnum memberRole, String activeYn) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.memberRole = memberRole;
        this.activeYn = activeYn;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> list = new ArrayList<>();
        list.add(() -> String.format("ROLE_%s", memberRole.name()));
        return list;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        //return email;
        return memberId;
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
