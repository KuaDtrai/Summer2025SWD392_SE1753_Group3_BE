package com.project.ibtss.service;

import com.project.ibtss.dto.response.DriverResponse;
import com.project.ibtss.dto.request.*;
import com.project.ibtss.dto.response.AccountDetailResponse;
import com.project.ibtss.dto.response.AccountManageResponse;
import com.project.ibtss.dto.response.AccountResponse;
import com.project.ibtss.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

//@Service
public interface AccountService {
    AccountResponse getAccount(Integer id);

    List<AccountManageResponse> getAllAccounts();

    AccountManageResponse addAccount(AccountRequest accountRequest);

//    String generateToken(Account account);

    AccountResponse updateRole(Integer id, Role role);

    AccountResponse updatePassword(UpdatePasswordRequest updatePasswordRequest);

    AccountManageResponse updateAccount(Integer id, AccountRequest accountRequest);
    AccountDetailResponse updateAccountInfo(UpdateAccountInfoRequest request);

    AccountResponse login(LoginRequest loginRequest);

    String register(RegisterRequest registerRequest);

    AccountDetailResponse accountDetail();

    AccountResponse setAccountActive(Integer id);

    Boolean isCorrectPassword(String password);

    Page<DriverResponse> getAvailableDrivers(LocalDateTime departureTime, LocalDateTime arrivalTime, Pageable pageable, String search);
}
