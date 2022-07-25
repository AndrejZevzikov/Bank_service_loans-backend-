package com.final_project.loans.mapper;

import com.final_project.loans.dto.LoanDto;
import com.final_project.loans.model.Loan;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class MapperDto {

    public LoanDto toLoanDto(Loan loan){
        return LoanDto.builder()
                .status(loan.getStatus().toString())
                .type(loan.getType().toString())
                .monthToReturn(loan.getMonthToReturn())
                .signDate(loan.getSignDate())
                .build();
    }

    public List<LoanDto> toLoanDtoList(List<Loan> loans){
        return loans.stream()
                .map(this::toLoanDto)
                .collect(Collectors.toList());
    }
}
