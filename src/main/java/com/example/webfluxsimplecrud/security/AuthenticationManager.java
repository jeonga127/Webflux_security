package com.example.webfluxsimplecrud.security;

import com.example.webfluxsimplecrud.jwt.JwtUtil;
import com.example.webfluxsimplecrud.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String userId = jwtUtil.getUserInfoFromToken(authToken);

        return Mono.just(jwtUtil.validateToken(authToken))
                .filter(valid -> valid)
                .switchIfEmpty(Mono.empty())
                .flatMap(valid -> memberRepository.findByUserId(userId))
                .map(member -> {
                    return new UsernamePasswordAuthenticationToken(
                            member.getUserId(),
                            null,
                            member.getAuthorities()
                    );
                });
    }
}
