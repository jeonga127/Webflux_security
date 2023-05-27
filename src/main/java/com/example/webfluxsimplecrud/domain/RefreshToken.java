package com.example.webfluxsimplecrud.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table(name = "refreshtoken")
@NoArgsConstructor
public class RefreshToken {
    @Id
    private Long id;
    private String refreshToken;
    private String userId;

    public RefreshToken(String refreshToken, Member member){
        this.refreshToken = refreshToken;
        this.userId = member.getUserId();
    }

    public RefreshToken updateToken(String token){
        this.refreshToken = token;
        return this;
    }
}
