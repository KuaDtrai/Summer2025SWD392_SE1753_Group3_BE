package com.project.ibtss.controller;

import com.project.ibtss.dto.request.AccountRequest;
import com.project.ibtss.dto.request.UpdateAccountInfoRequest;
import com.project.ibtss.dto.request.UpdateAccountStatus;
import com.project.ibtss.dto.request.UpdatePasswordRequest;
import com.project.ibtss.dto.response.*;
import com.project.ibtss.utilities.enums.Role;
import com.project.ibtss.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ApiResponse<Page<AccountManageResponse>> GetAllAccounts(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return ApiResponse.<Page<AccountManageResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(accountService.getAllAccounts(pageable))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<AccountManageResponse> addAccount(@RequestBody AccountRequest accountRequest) {
        return ApiResponse.<AccountManageResponse>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(accountService.addAccount(accountRequest))
                .build();
    }

    @PutMapping("/password")
    public ApiResponse<AccountResponse> UpdatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        return ApiResponse.<AccountResponse>builder()
                .code(HttpStatus.OK.value()).message("")
                .data(accountService.updatePassword(updatePasswordRequest))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ApiResponse<AccountManageResponse> UpdateAccount(@PathVariable Integer id, @RequestBody AccountRequest accountRequest) {
        return ApiResponse.<AccountManageResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(accountService.updateAccount(id, accountRequest))
                .build();
    }

    @PutMapping("/updateInfo")
    public ApiResponse<AccountDetailResponse> UpdateAccountInfo(@RequestBody UpdateAccountInfoRequest request) {
        return ApiResponse.<AccountDetailResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(accountService.updateAccountInfo(request))
                .build();
    }

    @PutMapping("/role/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ApiResponse<AccountResponse> UpdateRole(@PathVariable Integer id, @RequestBody Role role) {
        return ApiResponse.<AccountResponse>builder().code(HttpStatus.OK.value()).message("").data(accountService.updateRole(id, role)).build();
    }

    @PutMapping("/active/{id}/status")
    @PreAuthorize("hasAuthority('admin:update')")
    public ApiResponse<AccountResponse> updateStatusAccount(@PathVariable Integer id, @RequestBody UpdateAccountStatus updateAccountStatus) {
        System.out.println("🔴 Received from FE - isActive = " + updateAccountStatus.getIsActive());

        return ApiResponse.<AccountResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(accountService.setAccountStatus(id, updateAccountStatus))
                .build();
    }

    @GetMapping("/drivers")
    public ApiResponse<Page<DriverResponse>> getAvailableDrivers(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureTime,
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
