package com.final_project.loans.exception;

public class InvalidLoanStatusException extends Exception {
    public InvalidLoanStatusException(String message) {
        super(message);
    }
}