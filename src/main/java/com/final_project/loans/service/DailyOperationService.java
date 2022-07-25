package com.final_project.loans.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DailyOperationService { //TODO pasilieku kaip pvz optimizuoti

    public String getIdentityNumber(String token) {
        WebClient client = WebClient.builder()
                .baseUrl("http://localhost:8080/customer/get").build();

        String body = client.get()
                .header("Authorization", token)
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
        return body;
    }

    public String getCurrencyCode(Long id){
        WebClient client = WebClient.builder()
                .baseUrl("http://localhost:8080/currency/code/" + id).build();

        String body = client.get()
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
        return body;
    }
}
