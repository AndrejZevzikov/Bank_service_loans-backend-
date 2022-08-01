package com.final_project.loans.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class CustomerDto {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String identificationNumber;
    private String authority;
}