package com.final_project.loans.service;

import com.final_project.loans.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class DailyOperationService {

    public static final String GET_CUSTOMER_API = "http://localhost:8080/customer/get";
    public static final String GET_CURRENCY_CODE_BY_ID_API = "http://localhost:8080/currency/code/";
    public static final String GET_CURRENCY_ID_BY_CODE_API = "http://localhost:8080/currency/id/";

    public CustomerDto getCustomerDto(final String token) {
        log.info("start connect to 8080");
        WebClient client = WebClient.builder().baseUrl(GET_CUSTOMER_API).build();
        CustomerDto dto =  client.get()
                .header("Authorization", token)
                .exchange()
                .block()
                .bodyToMono(CustomerDto.class)
                .block();
        return dto;
    }

    public String getCurrencyCode(final Long id){
        log.info("trying get currency from 8080");
        WebClient client = WebClient.builder().baseUrl(GET_CURRENCY_CODE_BY_ID_API + id).build();
        return client.get()
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
    }

    public Long getCurrencyCode(final String code){
        WebClient client = WebClient.builder().baseUrl(GET_CURRENCY_ID_BY_CODE_API + code).build();
        return client.get()
                .exchange()
                .block()
                .bodyToMono(Long.class)
                .block();
    }
}