package com.final_project.loans.service;

import com.final_project.loans.dto.CustomerDto;
import com.final_project.loans.exception.CustomerDoNotHaveAccessException;
import com.final_project.loans.exception.NoSuchObjInDatabaseException;
import com.final_project.loans.model.Loan;
import com.final_project.loans.model.LoanReturnSchedule;
import com.final_project.loans.repository.LoanRepository;
import com.final_project.loans.repository.LoanReturnScheduleRepository;
import com.final_project.loans.validation.LoanReturnScheduleServiceValidation;
import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Paths.get;

@Service
@AllArgsConstructor
public class LoanReturnScheduleService {
    public static final String LOAN_NOT_FOUND = "Loan with id %d does not exist";
    public static final String LOAN_SCHEDULE_ABSOLUTE_PATH =
            "C:\\Users\\andre\\Desktop\\Final_Project\\Backend\\loans\\src\\main\\resources\\schedulePdf\\loan_%d.pdf";
    private final LoanReturnScheduleRepository loanReturnScheduleRepository;
    private final PdfService pdfService;
    private final DailyOperationService dailyOperationService;
    private final LoanRepository loanRepository;
    private final LoanReturnScheduleServiceValidation loanReturnScheduleServiceValidation;

    public List<LoanReturnSchedule> getLoanReturnScheduleByLoanId(Long id) {
        return loanReturnScheduleRepository.findLoanReturnScheduleByLoanId(id).stream()
                .filter(loanReturnSchedule -> loanReturnSchedule.getActualPayDate() == null)
                .collect(Collectors.toList());
    }

    public List<LoanReturnSchedule> generateScheduleForNewLoan(Loan loan) {
        Double amountToPay = loan.getAmount() * (loan.getPercentage() / 100 * 1) / loan.getMonthToReturn();
        List<LoanReturnSchedule> loanReturnScheduleList = new ArrayList<>();
        for (int i = 0; i < loan.getMonthToReturn(); i++) {
            loanReturnScheduleList.add(
                    LoanReturnSchedule.builder()
                            .leftToPay(amountToPay)
                            .loan(loan)
                            .currencyId(loan.getCurrencyId())
                            .amountToPay(amountToPay)
                            .planPayDate(loan.getSignDate().plusMonths(i + 1))
                            .build()
            );
        }
        return loanReturnScheduleList;
    }

    public Resource getReturnSchedulePdf(Long loanId, String token) throws IOException, NoSuchObjInDatabaseException, CustomerDoNotHaveAccessException, DocumentException {
        CustomerDto customer = dailyOperationService.getCustomerDto(token);
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new NoSuchObjInDatabaseException(String.format(LOAN_NOT_FOUND, loanId)));
        loanReturnScheduleServiceValidation.isUserHasAccess(customer, loan);
        pdfService.generateLoanSchedulePdf(customer, loan);
        Path path = get(String.format(LOAN_SCHEDULE_ABSOLUTE_PATH, loanId));
        return new UrlResource(path.toUri());
    }
}
