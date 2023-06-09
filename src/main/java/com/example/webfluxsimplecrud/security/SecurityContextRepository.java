package com.example.webfluxsimplecrud.security;

import com.example.webfluxsimplecrud.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(JwtUtil.ACCESS_TOKEN))
                .filter(accessHeader -> accessHeader.startsWith("Bearer"))
                .flatMap(accessHeader -> {
                    String accessToken = accessHeader.substring(7);
                    Authentication accessAuth = new UsernamePasswordAuthenticationToken(accessToken, accessToken);

                    return jwtUtil.validateToken(accessToken)
                            .flatMap((valid) -> {
                                if (valid)
                                    return this.authenticationManager.authenticate(accessAuth).map(SecurityContextImpl::new);
                                else {
                                    return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(JwtUtil.REFRESH_TOKEN))
                                            .filter(refreshHeader -> refreshHeader.startsWith("Bearer"))
                                            .flatMap(refreshHeader -> {
                                                String refreshToken = refreshHeader.substring(7);
                                                Authentication refreshAuth = new UsernamePasswordAuthenticationToken(refreshToken, refreshToken);

                                                return jwtUtil.refreshTokenValidation(refreshToken)
                                                        .flatMap((validate) -> {
                                                            if (validate) {
                                                                exchange.getResponse().getHeaders()
                                                                        .set(JwtUtil.ACCESS_TOKEN, jwtUtil.createToken(jwtUtil.getUserInfoFromToken(refreshToken), JwtUtil.ACCESS_TOKEN));
                                                                return this.authenticationManager.authenticate(refreshAuth)
                                                                        .map(SecurityContextImpl::new);
                                                            } else
                                                                return Mono.error(new RuntimeException("토큰이 없잖아! ╰（‵□′）╯"));
                                                        });
                                            });
                                }
                            });
                });
    }
}
