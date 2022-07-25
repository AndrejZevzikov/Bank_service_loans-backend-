package com.final_project.loans.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanReturnSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;
    private LocalDate planPayDate;
    private LocalDate actualPayDate;
    private Double amountToPay;
    private Double leftToPay;
    private Long currencyId;
    @ManyToMany
    @JoinTable(
            name = "schedule_payment",
            joinColumns = @JoinColumn(name = "current_schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_id"))
    private List<Payment> payments;
}
