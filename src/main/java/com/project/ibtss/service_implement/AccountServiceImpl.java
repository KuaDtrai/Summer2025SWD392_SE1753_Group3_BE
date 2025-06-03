package com.project.ibtss.service_implement;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.project.ibtss.dto.request.LoginRequest;
import com.project.ibtss.dto.request.RegisterRequest;
import com.project.ibtss.dto.response.AccountResponse;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.enums.Role;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.mapper.AccountMapper;
import com.project.ibtss.model.Account;
import com.project.ibtss.repository.AccountRepository;
import com.project.ibtss.repository.TokenRepository;
import com.project.ibtss.service.AccountService;
import com.project.ibtss.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JWTService jwtService;
    private final AccountMapper mapper;

    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    private String generateToken(Account account) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(account.getFullName())
                .issuer("aia.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", buildScope(account))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.warn("Can't create JWT token!", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(Account account) {
        Role role = account.getRole();
        return (role != null) ? role.getRoleName() : " ";
    }

    @Override
    public AccountResponse login(LoginRequest loginRequest) {
        Account account = accountRepository.findByFullName(loginRequest.getUsername());
        if (account == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), account.getPasswordHash())) {
            throw new AppException(ErrorCode.INCORRECT_PASSWORD);
        }

        AccountResponse accountResponse = mapper.toAccountResponse(account);
        accountResponse.setToken(generateToken(account));

        return accountResponse;
    }

    @Override
    public String register(RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            log.warn("Password and Confirm Password do not match");
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        if (accountRepository.findByFullName(registerRequest.getUsername()) != null) {
            log.warn("Username already exists");
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        Account user = Account.builder()
                .fullName(registerRequest.getUsername())
                .passwordHash(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        accountRepository.save(user);
        return "Success";
    }

    @Override
    public AccountResponse accountDetail() {
        String acc = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findByFullName(acc);
        if (account == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        return mapper.toAccountResponse(account);
    }
}