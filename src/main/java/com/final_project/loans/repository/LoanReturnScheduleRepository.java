package com.final_project.loans.repository;

import com.final_project.loans.model.LoanReturnSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanReturnScheduleRepository extends JpaRepository<LoanReturnSchedule, Long> {

    @Query(value = "SELECT * FROM loan_return_schedule r WHERE r.loan_id = :id",nativeQuery = true)
    List<LoanReturnSchedule> findLoanReturnScheduleByLoanId(@Param("id") Long id);
}
