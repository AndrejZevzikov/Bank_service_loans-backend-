package com.final_project.loans.preparedData;

import com.final_project.loans.enums.LoanStatus;
import com.final_project.loans.enums.LoanType;
import com.final_project.loans.model.Loan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class LoansPreparedData {

    @Bean
    public List<Loan> setUpLoans() {
        Loan loan1 = Loan.builder()
                .amount(10000.0)
                .currencyId(1L)
                .customerIdentificationNumber("484646464676")
                .description("Testing1")
                .monthToReturn(20)
                .percentage(2.2)
                .status(LoanStatus.ANSWERED)
                .type(LoanType.LEASING)
                .signDate(LocalDate.of(2022, 2, 12))
                .build();
        Loan loan2 = Loan.builder()
                .amount(1000.0)
                .currencyId(2L)
                .customerIdentificationNumber("54643131")
                .description("Testing2")
                .monthToReturn(20)
                .percentage(2.2)
                .status(LoanStatus.ANSWERED)
                .type(LoanType.CONSUMER_CREDIT)
                .signDate(LocalDate.of(2022, 1, 12))
                .build();
        Loan loan3 = Loan.builder()
                .amount(1000000.0)
                .currencyId(1L)
                .customerIdentificationNumber("888888")
                .description("Testing3")
                .monthToReturn(20)
                .percentage(2.2)
                .status(LoanStatus.DRAFT)
                .type(LoanType.HOUSE_LOAN)
                .signDate(LocalDate.of(2022, 2, 8))
                .build();

        return List.of(loan1, loan2, loan3);
    }
}
