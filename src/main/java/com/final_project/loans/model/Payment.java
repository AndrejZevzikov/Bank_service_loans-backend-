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
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(mappedBy = "payments", cascade = CascadeType.PERSIST)
    private List<LoanReturnSchedule> loanReturnSchedule;
    @ManyToMany(mappedBy = "payments", cascade = CascadeType.PERSIST)
    private List<ScheduleHistory> loanReturnScheduleHistory;
    private LocalDate date;
    private Double amount;
    private Long currencyId;
}
