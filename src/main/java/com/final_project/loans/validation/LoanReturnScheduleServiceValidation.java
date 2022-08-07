package com.final_project.loans.validation;

import com.final_project.loans.dto.CustomerDto;
import com.final_project.loans.exception.CustomerDoNotHaveAccessException;
import com.final_project.loans.model.Loan;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class LoanReturnScheduleServiceValidation {

    public static final String CUSTOMER_DO_NOT_HAVE_ACCESS = "Customer %s do not have access to get loan with id %d ";
    public static final String ADMIN = "ADMIN";

    public void isUserHasAccess(final CustomerDto customerDto, final Loan loan) throws CustomerDoNotHaveAccessException {
        if (!customerDto.getIdentificationNumber().equals(loan.getCustomerIdentificationNumber())
                && !customerDto.getAuthority().equals(ADMIN)) {
            throw new CustomerDoNotHaveAccessException(
                    String.format(CUSTOMER_DO_NOT_HAVE_ACCESS, customerDto.getUsername(), loan.getId()));
        }
    }
}