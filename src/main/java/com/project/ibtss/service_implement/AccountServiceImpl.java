package com.project.ibtss.service_implement;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.project.ibtss.dto.request.AccountRequest;
import com.project.ibtss.dto.request.LoginRequest;
import com.project.ibtss.dto.request.RegisterRequest;
import com.project.ibtss.dto.request.UpdatePasswordRequest;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, AccountRepository> implements AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JWTService jwtService;
    private final AccountMapper mapper;



    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountRepository repository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository, JWTService jwtService, AccountMapper mapper) {
        super(repository);
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
        this.mapper = mapper;
    }

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
            System.out.println(jwsObject.serialize());
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
    public AccountResponse getAccount(int id) {
        Account account = accountRepository.findById(id).orElse(null);
        return mapper.toAccountResponse(account);
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountResponse> accountResponses = new ArrayList<>();
        for (Account account : accounts) {
            accountResponses.add(mapper.toAccountResponse(account));
        }
        return accountResponses;
    }

    @Override
    public AccountResponse updateRole(int id, Role role) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            return mapper.toAccountResponse(null);
        }
        account.setRole(role);
        return mapper.toAccountResponse(accountRepository.save(account));
    }

    @Override
    public AccountResponse updatePassword(int id, UpdatePasswordRequest updatePasswordRequest) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {return mapper.toAccountResponse(null);}
        if (!account.getPasswordHash().equals(passwordEncoder.encode(updatePasswordRequest.getOldPassword()))) {return mapper.toAccountResponse(null);}
        account.setPasswordHash(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
        return mapper.toAccountResponse(accountRepository.save(account));
    }

    @Override
    public AccountResponse updateAccount(int id, AccountRequest accountRequest) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            return mapper.toAccountResponse(null);
        }
        account.setFullName(accountRequest.getFullName());
        account.setEmail(accountRequest.getEmail());
        account.setPhone(accountRequest.getPhone());
        account.setUpdatedDate(LocalDateTime.now());
        return mapper.toAccountResponse(accountRepository.save(account));
    }

    @Override
    public AccountResponse login(LoginRequest loginRequest) {
        Account account = accountRepository.findByPhone(loginRequest.getPhone());
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

        if (accountRepository.findByPhone(registerRequest.getPhone()) != null) {
            log.warn("Username already exists");
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        Account user = Account.builder()
                .phone(registerRequest.getPhone())
                .fullName(registerRequest.getFullName())
                .passwordHash(passwordEncoder.encode(registerRequest.getPassword()))
                .phone(registerRequest.getPhone())
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