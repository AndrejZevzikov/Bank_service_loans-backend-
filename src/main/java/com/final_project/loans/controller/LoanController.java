package com.final_project.loans.controller;

import com.final_project.loans.dto.LoanDto;
import com.final_project.loans.exception.CustomerDoNotHaveAccessException;
import com.final_project.loans.exception.NoSuchObjInDatabaseException;
import com.final_project.loans.factory.LoanServiceFactory;
import com.final_project.loans.helper.JwtDecoder;
import com.final_project.loans.mapper.MapperDto;
import com.final_project.loans.model.LoanReturnSchedule;
import com.final_project.loans.service.LoanReturnScheduleService;
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

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequestMapping("/loans")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@Slf4j
public class LoanController {
    private final LoanServiceFactory loanServiceFactory;
    private final MapperDto mapperDto;
    private final JwtDecoder jwtDecoder;
    private final LoanReturnScheduleService loanReturnScheduleService;


    @GetMapping
    public ResponseEntity<List<LoanDto>> getLoans(@RequestHeader("Authorization") String token) throws Exception {
        return ResponseEntity
                .ok()
                .body(mapperDto.toLoanDtoList(
                        loanServiceFactory.getService(jwtDecoder.getRole(token)).getLastFiveLoans(token)));
    }

    @GetMapping("pdf/{id}")
    public ResponseEntity<Resource> getReturnLoanSchedulePdf(@PathVariable(name = "id") Long id,@RequestHeader("Authorization") String token) throws IOException, NoSuchObjInDatabaseException, CustomerDoNotHaveAccessException, DocumentException {
        Resource resource = loanReturnScheduleService.getReturnSchedulePdf(id, token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Files.probeContentType(resource.getFile().toPath())))
                .headers(httpHeaders)
                .body(resource);


    }
}
