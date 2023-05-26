package com.example.webfluxsimplecrud.repository;

import com.example.webfluxsimplecrud.domain.Member;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface MemberRepository extends ReactiveCrudRepository<Member, Long> {
    Mono<Member> findByUserId(String userId);
    Mono<Boolean> existsByUserId(String userId);
}
