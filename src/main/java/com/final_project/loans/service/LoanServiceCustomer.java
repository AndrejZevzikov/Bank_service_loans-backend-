package com.final_project.loans.service;

import com.final_project.loans.model.Loan;
import com.final_project.loans.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LoanServiceCustomer implements LoanService{
    private final LoanRepository loanRepository;
    private final DailyOperationService dailyOperationService;


    @Override
    public List<Loan> getLastFiveLoans(String token) {
        return loanRepository.findByCustomerIdentificationNumber(
                dailyOperationService.getCustomerDto(token).getIdentificationNumber());
    }
}