package com.final_project.loans.service;

import com.final_project.loans.model.LoanReturnSchedule;
import com.final_project.loans.repository.LoanReturnScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LoanReturnScheduleService {
    private final LoanReturnScheduleRepository loanReturnScheduleRepository;

    public List<LoanReturnSchedule> getLoanReturnScheduleByLoanId(Long id){
        return loanReturnScheduleRepository.findLoanReturnScheduleByLoanId(id).stream()
                .filter(loanReturnSchedule -> loanReturnSchedule.getActualPayDate() == null)
                .collect(Collectors.toList());
    }

    public void getReturnSchedulePdf(Long id){
        //TODO pabaigti
    }
}
