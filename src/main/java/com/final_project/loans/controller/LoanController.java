package com.final_project.loans.controller;

import com.final_project.loans.dto.LoanDto;
import com.final_project.loans.enums.LoanStatus;
import com.final_project.loans.enums.LoanType;
import com.final_project.loans.exception.CustomerDoNotHaveAccessException;
import com.final_project.loans.exception.InvalidLoanStatusException;
import com.final_project.loans.exception.NoSuchObjInDatabaseException;
import com.final_project.loans.mapper.MapperDto;
import com.final_project.loans.service.LoanReturnScheduleService;
import com.final_project.loans.service.LoanService;
import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequestMapping("/loans")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@Slf4j
public class LoanController {
    private final LoanService loanService;
    private final MapperDto mapperDto;
    private final LoanReturnScheduleService loanReturnScheduleService;


    @GetMapping
    public ResponseEntity<List<LoanDto>> getLoans(@RequestHeader(AUTHORIZATION) String token) {
        return ResponseEntity
                .ok()
                .body(mapperDto.toLoanDtoList(loanService.getLastFiveLoans(token)));

    }

    @GetMapping("/id/{id}")
    ResponseEntity<LoanDto> getLoanById(@PathVariable(name = "id") Long id) throws NoSuchObjInDatabaseException {
        return ResponseEntity.ok()
                .body(mapperDto.toLoanDto(loanService.getLoanById(id)));
    }

    @GetMapping("pdf/{id}")
    public ResponseEntity<Resource> getReturnLoanSchedulePdf(@PathVariable(name = "id") Long id,
                                                             @RequestHeader(AUTHORIZATION) String token)
            throws IOException, NoSuchObjInDatabaseException, CustomerDoNotHaveAccessException, DocumentException {
        Resource resource = loanReturnScheduleService.getReturnSchedulePdf(id, token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Files.probeContentType(resource.getFile().toPath())))
                .headers(httpHeaders)
                .body(resource);
    }

    @GetMapping("/types")
    public ResponseEntity<List<LoanType>> getTypes() {
        return ResponseEntity.ok().body(loanService.getTypes());
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<LoanStatus>> getStatuses() {
        return ResponseEntity.ok().body(loanService.getStatuses());
    }

    @PutMapping("/edit")
    public ResponseEntity<LoanDto> editLoanStatus(@RequestHeader(AUTHORIZATION) String token,
                                                  @RequestBody LoanDto loanDto)
            throws NoSuchObjInDatabaseException, CustomerDoNotHaveAccessException, InvalidLoanStatusException {
        System.out.println(loanDto);
        return ResponseEntity.ok()
                .body(mapperDto.toLoanDto(loanService.editLoan(loanDto, token)));
    }
}