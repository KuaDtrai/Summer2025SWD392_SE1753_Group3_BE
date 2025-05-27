package com.project.ibtss.service;

import com.project.ibtss.model.Token;
import com.project.ibtss.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String extractEmail(String token);
    String generateAccessToken(UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    Token checkTokenIsValid(String tokenType, User user);
    String generateResetToken(UserDetails userDetails);
    boolean validateToken(String token);
}
