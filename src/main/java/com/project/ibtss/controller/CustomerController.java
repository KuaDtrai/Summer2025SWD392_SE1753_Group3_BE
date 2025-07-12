package com.project.ibtss.controller;

import com.project.ibtss.dto.request.CustomerRequest;
import com.project.ibtss.dto.response.AccountResponse;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.CustomerResponse;
import com.project.ibtss.enums.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private CustomerResponse getCustomerResponse() {
        return new CustomerResponse(1, "Kien Ho", "kien@gmail.com", "0912345678", LocalDate.now(), "");
    }

    @GetMapping
    public ApiResponse<CustomerResponse> getCustomer() {
        return ApiResponse.<CustomerResponse>builder().code(HttpStatus.OK.value()).message("").data(getCustomerResponse()).build();
    }

    @PostMapping
    public ApiResponse<CustomerResponse> postCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        AccountResponse account = new AccountResponse(1, "kien@gmail.com", "Kien Ho", "0123456789", Role.CUSTOMER, "");
        return ApiResponse.<CustomerResponse>builder().code(HttpStatus.OK.value()).message("").data(getCustomerResponse()).build();
    }

    @PutMapping
    public ApiResponse<CustomerResponse> updateCustomer(
            @Valid @RequestBody CustomerRequest customerRequest
    ) {
        AccountResponse account = new AccountResponse(1, "kien@gmail.com", "Kien Ho", "0123456789", Role.CUSTOMER, "");
        return ApiResponse.<CustomerResponse>builder().code(HttpStatus.OK.value()).message("").data(getCustomerResponse()).build();
    }
}
