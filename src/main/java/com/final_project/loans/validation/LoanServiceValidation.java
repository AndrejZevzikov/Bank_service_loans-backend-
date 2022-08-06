package com.final_project.loans.validation;

import com.final_project.loans.dto.LoanDto;
import com.final_project.loans.enums.LoanStatus;
import com.final_project.loans.exception.CustomerDoNotHaveAccessException;
import com.final_project.loans.exception.InvalidLoanStatusException;
import com.final_project.loans.helper.JwtDecoder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class LoanServiceValidation {
    public static final String ADMIN = "ADMIN";
    public static final String ONLY_ADMINS_CAN_EDIT_LOAN_STATUS = "Only admins can edit loan status";
    private final JwtDecoder jwtDecoder;

    public static final String LOAN_STATUS_NOT_VALID = "Loan status %s is not valid";

    public void canEditStatus(LoanDto loanDto, String token) throws CustomerDoNotHaveAccessException, InvalidLoanStatusException {
        isCanEditStatus(token);
        isStatusLoanValid(loanDto);
    }

    public void isStatusLoanValid(LoanDto loanDto) throws InvalidLoanStatusException {
        if (!Arrays.stream(LoanStatus.values())
                .collect(Collectors.toList())
                .contains(loanDto.getStatus())) {
            throw new InvalidLoanStatusException(String.format(LOAN_STATUS_NOT_VALID, loanDto.getStatus()));
        }
    }

    public void isCanEditStatus(String token) throws CustomerDoNotHaveAccessException {
        if (!jwtDecoder.getRole(token).equals(ADMIN)){
            throw new CustomerDoNotHaveAccessException(ONLY_ADMINS_CAN_EDIT_LOAN_STATUS);
        }
    }



}
