package com.final_project.loans.service;

import com.auth0.jwt.JWT;
import com.final_project.loans.model.Loan;
import com.final_project.loans.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final DailyOperationService dailyOperationService;

    public List<Loan> getFirstFiveLoans(String token) {
        if (JWT.decode(token.substring("Bearer ".length())).getClaims().get("roles").asList(String.class).get(0).equals("ADMIN")) {
           return loanRepository.findAll().stream()
                   .sorted(Comparator.comparing(Loan::getSignDate))
                   .limit(5)
                   .collect(Collectors.toList());
        }
        return loanRepository.findByCustomerIdentificationNumber(dailyOperationService.getIdentityNumber(token));
    }
}
