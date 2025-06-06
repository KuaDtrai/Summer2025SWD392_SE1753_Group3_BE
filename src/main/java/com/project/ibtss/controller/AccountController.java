package com.project.ibtss.controller;

import com.project.ibtss.dto.request.AccountRequest;
import com.project.ibtss.dto.request.UpdatePasswordRequest;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.enums.Permission;
import com.project.ibtss.enums.Role;
import com.project.ibtss.model.Account;
import com.project.ibtss.repository.AccountRepository;
import com.project.ibtss.service.AccountService;
import com.project.ibtss.service_implement.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Account")
public class AccountController {
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @GetMapping("/{id}")
    public ApiResponse GetAccountById(@PathVariable int id) {
        return ApiResponse.builder().code(HttpStatus.OK.value()).message("").data(accountService.getAccount(id)).build();
    }

    @GetMapping("")
    @PreAuthorize("Permission.ADMIN_READ")
    public ApiResponse GetAllAccounts() {
        return ApiResponse.builder().code(HttpStatus.OK.value()).message("").data(accountService.getAllAccounts()).build();
    }

//    public ApiResponse CreateAccount(Account account) {
//        return ApiResponse.builder().code(HttpStatus.OK.value()).message("").data(accountService)
//    }

    @PutMapping("/password/{id}")
    public ApiResponse UpdatePassword(@PathVariable int id,@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        return ApiResponse.builder().code(HttpStatus.OK.value()).message("").data(accountService.updatePassword(id, updatePasswordRequest)).build();
    }

    @PutMapping("/{id}")
    public ApiResponse UpdateAccount(@PathVariable int id, @RequestBody AccountRequest accountRequest) {
        return ApiResponse.builder().code(HttpStatus.OK.value()).message("").data(accountService.updateAccount(id, accountRequest)).build();
    }

    @PutMapping("/role/{id}")
    public ApiResponse UpdateRole(@PathVariable int id, @RequestBody Role role) {
        return ApiResponse.builder().code(HttpStatus.OK.value()).message("").data(accountService.updateRole(id, role)).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse DeleteAccount(@PathVariable int id) {
        return ApiResponse.builder().code(HttpStatus.OK.value()).message("").data(accountService.delete(id)).build();
    }
}
