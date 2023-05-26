package com.example.webfluxsimplecrud.domain;

import com.example.webfluxsimplecrud.dto.SignupRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Getter
@Table("member")
@NoArgsConstructor
public class Member implements UserDetails {
    @Id
    private Long id;
    private String userId;

    @JsonIgnore
    private String password;

    private String nickname;
    private String streamKey;
    private MemberRole role;

    public Member(SignupRequestDto signupRequestDto, String password, MemberRole role) {
        this.userId = signupRequestDto.getUserId();
        this.password = password;
        this.nickname = signupRequestDto.getNickname();
        this.streamKey = signupRequestDto.getStreamKey();
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority(this.getRole().toString())));
    }

    @Override
    public String getUsername() {
        return this.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
