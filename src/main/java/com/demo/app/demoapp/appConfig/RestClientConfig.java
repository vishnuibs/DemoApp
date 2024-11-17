package com.demo.app.demoapp.appConfig;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${demoClient.base.url}")
    private String BASE_URL;

    @Bean(name = "demoRestClient")
    RestClient getRestClient() {
       return RestClient.builder()
               .baseUrl(BASE_URL)
               .build();
    }
}
