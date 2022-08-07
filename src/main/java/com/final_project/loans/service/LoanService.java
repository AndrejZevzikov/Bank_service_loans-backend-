package com.final_project.loans.service;

import com.final_project.loans.dto.LoanDto;
import com.final_project.loans.enums.LoanStatus;
import com.final_project.loans.enums.LoanType;
import com.final_project.loans.exception.CustomerDoNotHaveAccessException;
import com.final_project.loans.exception.InvalidLoanStatusException;
import com.final_project.loans.exception.NoSuchObjInDatabaseException;
import com.final_project.loans.helper.JwtDecoder;
import com.final_project.loans.model.Loan;
import com.final_project.loans.repository.LoanRepository;
import com.final_project.loans.validation.LoanServiceValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.final_project.loans.enums.LoanStatus.IN_PROGRESS;

@Service
@AllArgsConstructor
@Slf4j
public class LoanService {
    public static final String LOAN_WITH_GIVEN_ID_DOES_NOT_EXIST = "Loan with id %d does not exist in database";
    public static final String ADMIN = "ADMIN";
    private final LoanRepository loanRepository;
    private final LoanReturnScheduleService loanReturnScheduleService;
    private final DailyOperationService dailyOperationService;
    private final JwtDecoder jwtDecoder;
    private final LoanServiceValidation loanServiceValidation;

    public List<Loan> getLastFiveLoans(String token) {
        String role = jwtDecoder.getRole(token);
        if (role.equals(ADMIN)) {
            return getLastFiveLoansForAdmin(token);
        }
        return getLastFiveLoansForCustomer(token);
    }

    public Loan getLoanById(Long id) throws NoSuchObjInDatabaseException {
        return loanRepository.findById(id).orElseThrow(
                () -> new NoSuchObjInDatabaseException(String.format(LOAN_WITH_GIVEN_ID_DOES_NOT_EXIST, id)));
    }

    public List<LoanType> getTypes() {
        log.info("Getting all types");
        return Arrays.stream(LoanType.values()).collect(Collectors.toList());
    }

    public List<LoanStatus> getStatuses() {
        log.info("Getting all statuses");
        return Arrays.stream(LoanStatus.values()).collect(Collectors.toList());
    }

    public Loan editLoan(LoanDto loanDto, String token)
            throws CustomerDoNotHaveAccessException, InvalidLoanStatusException, NoSuchObjInDatabaseException {
        loanServiceValidation.canEditStatus(loanDto, token);
        Loan loan = loanRepository.findById(loanDto.getId()).orElseThrow(() ->
                new NoSuchObjInDatabaseException(String.format(LOAN_WITH_GIVEN_ID_DOES_NOT_EXIST, loanDto.getId())));
        log.info("editing loan with id " + loanDto.getId());
        LoanStatus oldStatus = loan.getStatus();
        Loan loanToReturn = mapEditingForLoan(loanDto, loan);
        generateReturnScheduleIfAnswered(loanToReturn, oldStatus);
        return loan;
    }

    private Loan mapEditingForLoan(LoanDto loanDto, Loan loan) {
        loan.setStatus(loanDto.getStatus());
        loan.setMonthToReturn(loanDto.getMonthToReturn());
        loan.setType(loanDto.getType());
        loan.setSignDate(loanDto.getSignDate());
        loan.setAmount(loanDto.getAmount());
        loan.setCurrencyId(dailyOperationService.getCurrencyCode(loanDto.getCurrencyCode()));
        loan.setPercentage(loanDto.getPercentage());
        return loanRepository.save(loan);
    }

    private void generateReturnScheduleIfAnswered(Loan newLoan, LoanStatus oldStatus) {
        log.info("Old status was {}", oldStatus);
        log.info("New status was {}", newLoan.getStatus());
        if (newLoan.getStatus().equals(IN_PROGRESS) && !oldStatus.equals(IN_PROGRESS)) {
            loanReturnScheduleService.saveReturnSchedule(loanReturnScheduleService.generateScheduleForNewLoan(newLoan));
        }
    }


    private List<Loan> getLastFiveLoansForCustomer(String token) {
        log.info("getting last loans for customer");
        return loanRepository.findByCustomerIdentificationNumber(
                dailyOperationService.getCustomerDto(token).getIdentificationNumber());
    }

    private List<Loan> getLastFiveLoansForAdmin(final String token) {
        log.info("Admin {} getting last loans", jwtDecoder.getUsername(token));
        return loanRepository.findAll().stream()
                .sorted(Comparator.comparing(Loan::getSignDate))
                .limit(5)
                .collect(Collectors.toList());
    }
}