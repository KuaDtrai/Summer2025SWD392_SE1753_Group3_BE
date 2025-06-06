package com.project.ibtss.service;

import com.project.ibtss.dto.request.AccountRequest;
import com.project.ibtss.dto.request.LoginRequest;
import com.project.ibtss.dto.request.RegisterRequest;
import com.project.ibtss.dto.request.UpdatePasswordRequest;
import com.project.ibtss.dto.response.AccountResponse;
import com.project.ibtss.dto.response.UserResponse;
import com.project.ibtss.enums.Role;
import com.project.ibtss.model.Account;

import java.util.List;

//@Service
public interface AccountService extends BaseService<Account> {
    AccountResponse getAccount(int id);

    List<AccountResponse> getAllAccounts();

    AccountResponse updateRole(int id, Role role);

    AccountResponse updatePassword(int id, UpdatePasswordRequest updatePasswordRequest);

    AccountResponse updateAccount(int id, AccountRequest accountRequest);

    AccountResponse login(LoginRequest loginRequest);

    String register(RegisterRequest registerRequest);

    AccountResponse accountDetail();
}
