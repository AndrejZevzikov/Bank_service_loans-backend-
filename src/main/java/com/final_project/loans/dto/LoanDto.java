package com.final_project.loans.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;


@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoanDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String type;
    private String status;
    private LocalDate signDate;
    private Integer monthToReturn;
}
