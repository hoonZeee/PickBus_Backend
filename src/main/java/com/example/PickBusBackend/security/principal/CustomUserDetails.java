package com.example.PickBusBackend.security.principal;

import com.example.PickBusBackend.repository.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 만료 정책 없으면 true
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 차단 기능 넣을거면 user.getStatus() 확인
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 패스워드 만료 정책 없으면 true
    }

    @Override
    public boolean isEnabled() {
        return true; // 보통 user.getStatus() == ACTIVE 로 체크
    }

}
