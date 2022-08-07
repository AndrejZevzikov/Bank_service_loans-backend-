package com.final_project.loans.helper;

import com.auth0.jwt.JWT;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JwtDecoder {

    public String getRole(String token){
        return JWT.decode(token.substring("Bearer ".length())).getClaims().get("roles").asList(String.class).get(0);
    }

    public String getUsername(String token){
        return JWT.decode(token.substring("Bearer ".length())).getClaim("sub").asString();
    }
}