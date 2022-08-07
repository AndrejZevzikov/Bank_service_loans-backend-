package com.final_project.loans.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ScheduleHistory {

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
            name = "schedule_history_payment",
            joinColumns = @JoinColumn(name = "history_schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_id"))
    private List<Payment> payments;
}