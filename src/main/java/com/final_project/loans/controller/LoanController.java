package com.final_project.loans.controller;

import com.auth0.jwt.JWT;
import com.final_project.loans.dto.LoanDto;
import com.final_project.loans.mapper.MapperDto;
import com.final_project.loans.service.DailyOperationService;
import com.final_project.loans.service.LoanService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@Slf4j
public class LoanController {
    private final LoanService loanService;
    private final MapperDto mapperDto;


    @GetMapping
    public ResponseEntity<List<LoanDto>> getLoans(@RequestHeader("Authorization") String token) {
        return ResponseEntity
                .ok()
                .body(mapperDto.toLoanDtoList(loanService.getFirstFiveLoans(token)));
    }

    @GetMapping("pdf/{id}")
    public void getReturnLoanSchedulePdf(@PathVariable(name = "id")Long id){

    }
}
