package com.example.webfluxsimplecrud.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;

@Configuration
@RequiredArgsConstructor
@EnableReactiveMethodSecurity
public class ReactiveSecurityConfig {

    private final ApplicationContext applicationContext;

}
