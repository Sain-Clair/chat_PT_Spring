package com.chun.springpt.vo;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Alias("user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO implements UserDetails {

    private String id;
    private String email;
    private String password;
    private String name;
    private String gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    // 사용자의 권한 정보 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    // 사용자의 식별자 반환
    @Override
    public String getUsername() {
        return this.id;
    }

    // 사용자의 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 사용자의 계정 잠김 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 사용자의 패스워드 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 사용자의 활성화 여부 반환
    @Override
    public boolean isEnabled() {
        return true;
    }
}