package com.final_project.loans.factory;

import com.final_project.loans.service.LoanServiceAdmin;
import com.final_project.loans.service.LoanServiceCustomer;
import com.final_project.loans.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoanServiceFactory {

    private final LoanServiceAdmin adminLoanService;
    private final LoanServiceCustomer customerLoanService;

    public LoanService getService(String role) throws Exception { //TODO atsikratyti
        if (role.equals("ADMIN")) return adminLoanService;
        if (role.equals("CLIENT")) return customerLoanService;
        throw new Exception("test");
    }
}