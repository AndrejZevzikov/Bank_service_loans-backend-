package com.final_project.loans.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoanDto {

    private Long id;
    private String type;
    private String status;
    private LocalDate signDate;
    private Integer monthToReturn;
    private Double amount;
    private String currencyCode;
}