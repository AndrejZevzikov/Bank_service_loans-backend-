package com.final_project.loans.mapper;

import com.final_project.loans.dto.LoanDto;
import com.final_project.loans.model.Loan;
import com.final_project.loans.service.DailyOperationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MapperDto {
    private final DailyOperationService dailyOperationService;

    public LoanDto toLoanDto(final Loan loan){
        return LoanDto.builder()
                .status(loan.getStatus().toString())
                .type(loan.getType().toString())
                .monthToReturn(loan.getMonthToReturn())
                .signDate(loan.getSignDate())
                .id(loan.getId())
                .amount(loan.getAmount())
                .currencyCode(dailyOperationService.getCurrencyCode(loan.getCurrencyId()))
                .build();
    }

    public List<LoanDto> toLoanDtoList(List<Loan> loans){
        return loans.stream()
                .map(this::toLoanDto)
                .collect(Collectors.toList());
    }
}
