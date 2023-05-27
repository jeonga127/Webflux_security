package com.example.webfluxsimplecrud.service;

import com.example.webfluxsimplecrud.domain.Member;
import com.example.webfluxsimplecrud.domain.MemberRoleEnum;
import com.example.webfluxsimplecrud.domain.RefreshToken;
import com.example.webfluxsimplecrud.dto.LoginRequestDto;
import com.example.webfluxsimplecrud.dto.SignupRequestDto;
import com.example.webfluxsimplecrud.dto.TokenDto;
import com.example.webfluxsimplecrud.jwt.JwtUtil;
import com.example.webfluxsimplecrud.repository.MemberRepository;
import com.example.webfluxsimplecrud.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Mono<ResponseEntity<String>> signup(SignupRequestDto signupRequestDto) {
        return memberRepository.existsByUserId(signupRequestDto.getUserId())
                .flatMap(exists -> {
                    if (exists)
                        return Mono.error(new IllegalArgumentException("중복된 아이디입니다."));
                    else {
                        MemberRoleEnum role = signupRequestDto.getMemberRole().equals("ADMIN") ? MemberRoleEnum.ADMIN : MemberRoleEnum.USER
                                ;
                        String password = passwordEncoder.encode(signupRequestDto.getPassword());
                        return memberRepository.save(new Member(signupRequestDto, password, role))
                                .onErrorResume(exception -> {
                                    return Mono.error(new RuntimeException("회원 정보 저장 중 오류 발생!"));
                                })
                                .thenReturn(ResponseEntity.ok(signupRequestDto.getNickname() + "님 회원 가입 완료 o(〃＾▽＾〃)o"));
                    }
                });
    }

    @Transactional
    public Mono<ResponseEntity<String>> login(LoginRequestDto loginRequestDto) {
        return memberRepository.findByUserId(loginRequestDto.getUserId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("존재하지 않는 사용자입니다.")))
                .flatMap(member -> {
                    if (passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
                        TokenDto tokenDto = jwtUtil.createAllToken(member.getUserId());

                        return refreshTokenRepository.findByUserId(member.getUserId())
                                .switchIfEmpty(refreshTokenRepository.save(new RefreshToken(tokenDto.getRefreshToken(), member)))
                                .flatMap(refreshToken -> refreshTokenRepository.save(refreshToken.updateToken(tokenDto.getRefreshToken())))
                                .onErrorResume(exception -> {
                                    return Mono.error(new RuntimeException("Refresh Token 저장 중 오류 발생!"));
                                })
                                .map(refreshToken -> {
                                    HttpHeaders header = new HttpHeaders();

                                    header.add(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
                                    header.add(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
                                    return ResponseEntity.ok().headers(header).body("로그인 성공 o(〃＾▽＾〃)o");
                                });
                    } else
                        return Mono.error(new IllegalArgumentException("비밀번호가 틀렸습니다."));
                });
    }
}
