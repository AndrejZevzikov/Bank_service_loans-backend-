package com.final_project.loans.exception;

public class CustomerDoNotHaveAccessException extends Exception {

    public CustomerDoNotHaveAccessException(String message) {
        super(message);
    }
}