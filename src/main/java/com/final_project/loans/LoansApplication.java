package com.final_project.loans;

import com.final_project.loans.model.LoanReturnSchedule;
import com.final_project.loans.preparedData.LoanReturnSchedulePreparedData;
import com.final_project.loans.preparedData.LoansPreparedData;
import com.final_project.loans.repository.LoanRepository;
import com.final_project.loans.repository.LoanReturnScheduleRepository;
import com.final_project.loans.service.LoanReturnScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EntityScan(basePackages = {"com/final_project/loans/*"})
public class LoansApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoansApplication.class, args);

    }

    @Bean
    public CommandLineRunner run(final LoanRepository loanRepository, @Autowired LoansPreparedData loansPreparedData,
                                 final LoanReturnScheduleRepository loanReturnScheduleRepository,
                                 @Autowired LoanReturnSchedulePreparedData loanReturnSchedulePreparedData,
                                 @Autowired LoanReturnScheduleService service) {
        return args -> {
            loanRepository.saveAll(loansPreparedData.setUpLoans());
            List<LoanReturnSchedule> loanReturnSchedules = loanReturnSchedulePreparedData.setUpSchedule();
            loanReturnSchedules.forEach(loanReturnSchedule -> loanReturnSchedule.setLoan(loanRepository.findById(2L).get()));
            loanReturnScheduleRepository.saveAll(loanReturnSchedules);
            service.getLoanReturnScheduleByLoanId(1L).forEach(loanReturnSchedule -> System.out.println(loanReturnSchedule.getPlanPayDate()));
        };
    }

}
