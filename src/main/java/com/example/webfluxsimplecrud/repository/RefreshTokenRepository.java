package com.example.webfluxsimplecrud.repository;

import com.example.webfluxsimplecrud.domain.RefreshToken;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface RefreshTokenRepository extends ReactiveCrudRepository<RefreshToken, Long> {
    Mono<RefreshToken> findByUserId(String userId);
    Mono<Boolean> existsByUserId(String userId);
}
