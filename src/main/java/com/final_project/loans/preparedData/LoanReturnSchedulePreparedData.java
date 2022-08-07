package com.final_project.loans.preparedData;

import com.final_project.loans.model.LoanReturnSchedule;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
@AllArgsConstructor
public class LoanReturnSchedulePreparedData {

    @Bean
    public List<LoanReturnSchedule> setUpSchedule() {
        LoanReturnSchedule record1 = LoanReturnSchedule.builder()
                .planPayDate(LocalDate.of(2022, 7, 22))
                .actualPayDate(LocalDate.of(2022, 7, 22))
                .amountToPay(5000.0)
                .leftToPay(0.0)
                .currencyId(1L)
                .build();
        LoanReturnSchedule record2 = LoanReturnSchedule.builder()
                .planPayDate(LocalDate.of(2022, 7, 28))
                .amountToPay(5000.0)
                .leftToPay(5000.0)
                .currencyId(1L)
                .build();

        return List.of(record1, record2);
    }
}
