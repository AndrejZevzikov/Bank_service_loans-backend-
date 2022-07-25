package com.final_project.loans;

import com.final_project.loans.preparedData.LoansPreparedData;
import com.final_project.loans.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com/final_project/loans/*"})
@EntityScan(basePackages = {"com/final_project/loans/*"})
public class LoansApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoansApplication.class, args);
        System.out.println("Hello world ");

    }

    @Bean
    public CommandLineRunner run(final LoanRepository loanRepository, @Autowired LoansPreparedData loansPreparedData) {
        return args -> {
            loanRepository.saveAll(loansPreparedData.setUpLoans());
        };
    }

}
