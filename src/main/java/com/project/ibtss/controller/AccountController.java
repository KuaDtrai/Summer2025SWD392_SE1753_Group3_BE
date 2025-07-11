package com.project.ibtss.controller;

import com.project.ibtss.dto.request.AccountRequest;
import com.project.ibtss.dto.request.UpdatePasswordRequest;
import com.project.ibtss.dto.response.AccountResponse;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.enums.Permission;
import com.project.ibtss.enums.Role;
import com.project.ibtss.model.Account;
import com.project.ibtss.repository.AccountRepository;
import com.project.ibtss.service.AccountService;
import com.project.ibtss.service_implement.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ApiResponse<AccountResponse> GetAccountById(@PathVariable Integer id) {
        return ApiResponse.<AccountResponse>builder().code(HttpStatus.OK.value()).message("").data(accountService.getAccount(id)).build();
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('admin:read')")
    public ApiResponse<List<AccountResponse>> GetAllAccounts() {
        return ApiResponse.<List<AccountResponse>>builder().code(HttpStatus.OK.value()).message("").data(accountService.getAllAccounts()).build();
    }

    @PutMapping("/password")
    public ApiResponse<AccountResponse> UpdatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        return ApiResponse.<AccountResponse>builder().code(HttpStatus.OK.value()).message("").data(accountService.updatePassword(updatePasswordRequest)).build();
    }

    @PutMapping("")
    public ApiResponse<AccountResponse> UpdateAccount(@RequestBody AccountRequest accountRequest) {
        return ApiResponse.<AccountResponse>builder().code(HttpStatus.OK.value()).message("").data(accountService.updateAccount(accountRequest)).build();
    }

    @PutMapping("/role/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ApiResponse<AccountResponse> UpdateRole(@PathVariable Integer id, @RequestBody Role role) {
        return ApiResponse.<AccountResponse>builder().code(HttpStatus.OK.value()).message("").data(accountService.updateRole(id, role)).build();
    }

    @PutMapping("/active/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ApiResponse<AccountResponse> UpdateActiveAccount(@PathVariable Integer id) {
        return ApiResponse.<AccountResponse>builder().code(HttpStatus.OK.value()).message("").data(accountService.setAccountActive(id)).build();
    }

    @GetMapping("/drivers")
    public ApiResponse<Page<DriverResponse>> getAvailableDrivers(  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureTime,
                                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalTime,
                                                                   @RequestParam(defaultValue = "1") int page,
                                                                   @RequestParam(defaultValue = "") String search){
        final int DEFAULT_PAGE_SIZE = 10;
        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE);
        return ApiResponse.<Page<DriverResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(accountService.getAvailableDrivers(departureTime, arrivalTime, pageable, search))
                .build();
    }
}
