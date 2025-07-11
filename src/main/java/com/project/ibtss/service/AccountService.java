package com.project.ibtss.service;

import com.project.ibtss.controller.DriverResponse;
import com.project.ibtss.dto.request.AccountRequest;
import com.project.ibtss.dto.request.LoginRequest;
import com.project.ibtss.dto.request.RegisterRequest;
import com.project.ibtss.dto.request.UpdatePasswordRequest;
import com.project.ibtss.dto.response.AccountResponse;
import com.project.ibtss.enums.Role;
import com.project.ibtss.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

//@Service
public interface AccountService {
    AccountResponse getAccount(Integer id);

    List<AccountResponse> getAllAccounts();

//    String generateToken(Account account);

    AccountResponse updateRole(Integer id, Role role);

    AccountResponse updatePassword(UpdatePasswordRequest updatePasswordRequest);

    AccountResponse updateAccount(AccountRequest accountRequest);

    AccountResponse login(LoginRequest loginRequest);

    String register(RegisterRequest registerRequest);

    AccountResponse accountDetail();

    AccountResponse setAccountActive(Integer id);

    Page<DriverResponse> getAvailableDrivers(LocalDateTime departureTime, LocalDateTime arrivalTime, Pageable pageable, String search);
}
