package com.skcc.config;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class CircuitConfig {
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
        .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(3)).build())
        .circuitBreakerConfig(
            CircuitBreakerConfig.custom()
            .waitDurationInOpenState(Duration.ofSeconds(CircuitBreakerConfig.DEFAULT_SLOW_CALL_DURATION_THRESHOLD))
            .permittedNumberOfCallsInHalfOpenState(CircuitBreakerConfig.DEFAULT_PERMITTED_CALLS_IN_HALF_OPEN_STATE)
            // .slidingWindowSize(CircuitBreakerConfig.DEFAULT_SLIDING_WINDOW_SIZE)
            .slidingWindowSize(5)
            .slidingWindowType(CircuitBreakerConfig.DEFAULT_SLIDING_WINDOW_TYPE)
            .minimumNumberOfCalls(CircuitBreakerConfig.DEFAULT_MINIMUM_NUMBER_OF_CALLS)
            .failureRateThreshold(CircuitBreakerConfig.DEFAULT_FAILURE_RATE_THRESHOLD)
            .automaticTransitionFromOpenToHalfOpenEnabled(false)
            .slowCallRateThreshold(CircuitBreakerConfig.DEFAULT_SLOW_CALL_RATE_THRESHOLD)
            .slowCallDurationThreshold(Duration.ofSeconds(CircuitBreakerConfig.DEFAULT_SLOW_CALL_DURATION_THRESHOLD))
            .writableStackTraceEnabled(CircuitBreakerConfig.DEFAULT_WRITABLE_STACK_TRACE_ENABLED)
            .build()
        )
        .build());
    }
}