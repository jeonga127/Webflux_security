package com.example.webfluxsimplecrud.domain;

import com.example.webfluxsimplecrud.dto.SignupRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Table("member")
@NoArgsConstructor
public class Member{
    @Id
    private Long id;
    private String userId;

    @JsonIgnore
    private String password;

    private String nickname;
    private String streamKey;
    private MemberRoleEnum role;

    public Member(SignupRequestDto signupRequestDto, String password, MemberRoleEnum role) {
        this.userId = signupRequestDto.getUserId();
        this.password = password;
        this.nickname = signupRequestDto.getNickname();
        this.streamKey = signupRequestDto.getStreamKey();
        this.role = role;
    }
}
