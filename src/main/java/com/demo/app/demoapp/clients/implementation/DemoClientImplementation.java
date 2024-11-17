package com.demo.app.demoapp.clients.implementation;

import com.demo.app.demoapp.clients.DemoClient;
import com.demo.app.demoapp.exceptions.ClientErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
public class DemoClientImplementation implements DemoClient {

    private final String CLIENT_URL = "client/response";
    private RestClient restClient;

    public DemoClientImplementation(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public String getResponse() {


        return restClient.get()
                .uri(CLIENT_URL)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req,res) -> {
                    log.error(new String(res.getBody().readAllBytes()));
                    throw new ClientErrorException(new String(res.getBody().readAllBytes()));
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req,res) -> {
                    log.error(new String(res.getBody().readAllBytes()));
                    throw new ClientErrorException(new String(res.getBody().readAllBytes()));
                })
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
