package com.final_project.loans.dto;

import com.final_project.loans.enums.LoanStatus;
import com.final_project.loans.enums.LoanType;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class LoanDto {

    private Long id;
    private LoanType type;
    private LoanStatus status;
    private LocalDate signDate;
    private Integer monthToReturn;
    private Double amount;
    private String currencyCode;
    private Double percentage;
}