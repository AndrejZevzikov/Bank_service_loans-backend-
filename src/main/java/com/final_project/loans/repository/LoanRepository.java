package com.final_project.loans.repository;

import com.final_project.loans.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {
    List<Loan> findByCustomerIdentificationNumber(String customerIdentificationNumber);
}
