package com.demo.app.demoapp.clients.implementation;

import com.demo.app.demoapp.clients.DemoClient;
import com.demo.app.demoapp.exceptions.ClientErrorException;
import com.demo.app.demoapp.exceptions.FallbackException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;

@Service
@Slf4j
public class DemoClientImplementation implements DemoClient {

    private final String CLIENT_URL = "client/response";
    private RestClient restClient;

    public DemoClientImplementation(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    @Retry(name = "demoClientRetry" , fallbackMethod = "getResponseFallbackRetry")
    @RateLimiter(name = "demoClientRateLimiter", fallbackMethod =  "getResponseFallbackRateLimiter")
    @CircuitBreaker(name = "demoClientCircuitBreaker",  fallbackMethod =  "getResponseFallbackCircuitBreaker")
    public String getResponse() {

        return restClient.get()
                .uri(CLIENT_URL)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req,res) -> {
                    log.error(new String(res.getBody().readAllBytes()));
                    throw new ClientErrorException("Client Error: "+res.getStatusCode().toString());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req,res) -> {
                    log.info(res.getStatusCode().toString());
                    log.error(new String(res.getBody().readAllBytes()));
                    throw new ClientErrorException("Client Error: "+res.getStatusCode().toString());
                })
                .body(new ParameterizedTypeReference<>() {
                });
    }


    public String getResponseFallbackRetry(Exception throwable) {
        log.error("Retry: Fallback occurred due to : {}", throwable.getMessage());
        //for demo
        throw new FallbackException("Retry: Fallback Error Happened. " + throwable.getMessage());

    }

    public String getResponseFallbackRateLimiter(Throwable throwable) {
        log.error("RateLimiter: Rate limited : {}", throwable.getMessage());
        //for demo
        throw new FallbackException("RateLimiter: Rate limited.");

    }

    public String getResponseFallbackCircuitBreaker(Throwable throwable) {
        log.error("CircuitBreaker: Open: {}", throwable.getMessage());

        throw new FallbackException("CircuitBreaker: Open.");

    }
}
