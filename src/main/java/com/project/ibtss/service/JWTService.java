package com.project.ibtss.service;

import com.project.ibtss.model.Account;
import com.project.ibtss.model.Token;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String extractEmail(String token);
    String extractPhone(String token);
    String generateAccessToken(Account account);
    String generateRefreshToken(Account userDetails);
//    Token checkTokenIsValid(String tokenType, User user);
    String generateResetToken(Account userDetails);
    boolean validateToken(String token);

    Token checkTokenIsValid(String tokenType, Account account);
}
