package com.final_project.loans.service;

import com.final_project.loans.dto.CustomerDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DailyOperationService {

    public static final String GET_CUSTOMER_API = "http://localhost:8080/customer/get";
    public static final String GET_CURRENCY_CODE_BY_ID_API = "http://localhost:8080/currency/code/";

    public CustomerDto getCustomerDto(final String token) {
        WebClient client = WebClient.builder().baseUrl(GET_CUSTOMER_API).build();
        return client.get()
                .header("Authorization", token)
                .exchange()
                .block()
                .bodyToMono(CustomerDto.class)
                .block();
    }

    public String getCurrencyCode(final Long id){
        WebClient client = WebClient.builder().baseUrl(GET_CURRENCY_CODE_BY_ID_API + id).build();
        return client.get()
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
    }
}