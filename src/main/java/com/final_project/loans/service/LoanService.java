package com.final_project.loans.service;

import com.final_project.loans.model.Loan;

import java.util.List;

public interface LoanService { //TODO suzinot ar geras approach'as

    List<Loan> getLastFiveLoans(String token);
}
