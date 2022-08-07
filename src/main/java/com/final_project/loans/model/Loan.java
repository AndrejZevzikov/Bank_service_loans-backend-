package com.final_project.loans.model;

import com.final_project.loans.enums.LoanStatus;
import com.final_project.loans.enums.LoanType;
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
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerIdentificationNumber;
    @Enumerated(EnumType.STRING)
    private LoanType type;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    private LoanStatus status;
    private LocalDate signDate;
    private Double amount;
    private Long currencyId;
    private Integer monthToReturn;
    private Double percentage;
    @OneToMany(mappedBy = "loan",cascade = CascadeType.REMOVE)
    private List<LoanDocument> documents;
    @OneToMany(mappedBy = "loan", cascade = CascadeType.REMOVE)
    private List<LoanReturnSchedule> returnPaymentsSchedule;
    @OneToMany(mappedBy = "loan",cascade = CascadeType.REMOVE)
    private List<ScheduleHistory> loanReturnPaymentsScheduleHistory;
}