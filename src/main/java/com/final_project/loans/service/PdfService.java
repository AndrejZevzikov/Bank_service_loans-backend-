package com.final_project.loans.service;

import com.final_project.loans.dto.CustomerDto;
import com.final_project.loans.model.Loan;
import com.final_project.loans.model.LoanReturnSchedule;
import com.final_project.loans.repository.LoanReturnScheduleRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Component
@AllArgsConstructor
public class PdfService {
    public static final String LOAN_RETURN_SCHEDULE_ABSOLUTE_PATH = "C:\\Users\\andre\\Desktop\\Final_Project\\Backend\\loans\\src\\main\\resources\\schedulePdf\\loan_%d.pdf";
    private final LoanReturnScheduleRepository loanReturnScheduleRepository;

    public void generateLoanSchedulePdf(CustomerDto customer, Loan loan) throws FileNotFoundException, DocumentException {
        System.out.println("start creating file");
        List<LoanReturnSchedule> schedule = loanReturnScheduleRepository.findUnpaidScheduleByLoanId(loan.getId());
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(String.format(LOAN_RETURN_SCHEDULE_ABSOLUTE_PATH, loan.getId())));
        document.open();
        setUpTitle(document, loan, customer);
        setUpLoanPayments(document, schedule);
        document.close();
    }


    private void setUpTitle(Document document, Loan loan, CustomerDto customer) throws DocumentException {
        Chunk title = new Chunk(String.format("Dear %s, your loan nr. %d return payment schedule"
                , customer.getLastName(), loan.getId()),
                new Font(Font.FontFamily.COURIER, 16f, Font.BOLD, BaseColor.BLUE));
        document.add(title);
    }

    private void setUpLoanPayments(Document document, List<LoanReturnSchedule> schedule) throws DocumentException {

        for (LoanReturnSchedule loanReturnSchedule : schedule) {
            document.add(new Paragraph(new Chunk(loanReturnSchedule.getPlanPayDate() + " - " + loanReturnSchedule.getLeftToPay())));
        }
    }
}
