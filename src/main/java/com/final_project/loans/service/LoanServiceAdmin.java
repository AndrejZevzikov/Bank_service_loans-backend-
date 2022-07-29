package com.final_project.loans.service;

import com.final_project.loans.helper.JwtDecoder;
import com.final_project.loans.model.Loan;
import com.final_project.loans.repository.LoanRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class LoanServiceAdmin implements LoanService {
    private final LoanRepository loanRepository;
    private final DailyOperationService dailyOperationService;
    private final JwtDecoder jwtDecoder;

    @Override
    public List<Loan> getLastFiveLoans(String token) {
        log.info("Admin {} getting last loans", jwtDecoder.getUsername(token));
        return loanRepository.findAll().stream()
                .sorted(Comparator.comparing(Loan::getSignDate))
                .limit(5)
                .collect(Collectors.toList());
    }

//    public Loan saveNewLoan(Loan loanToSave, String username){
//       return null;
//    }
//
//    public Loan acceptLoan(Long id, String Role){
//        return null;
//    }
}
