package com.project.ibtss.controller;

import com.project.ibtss.dto.request.StaffRequest;
import com.project.ibtss.dto.response.AccountResponse;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.StaffResponse;
import com.project.ibtss.enums.Position;
import com.project.ibtss.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/staff")
public class StaffController {
//    private StaffResponse getStaffResponse() {
//        return new StaffResponse(1, "Kien Ho", "kien@gmail.com", "0912345678", Position.SELLER, LocalDate.now());
//    }

//    @PostMapping
//    public ApiResponse<StaffResponse> addStaff(@RequestBody StaffRequest staffRequest) {
//        return ApiResponse.<StaffResponse>builder()
//                .code(HttpStatus.OK.value())
//                .message("Successfully added staff")
//                .data(getStaffResponse())
//                .build();
//    }
//
//    @GetMapping
//    public ApiResponse<StaffResponse> getStaff() {
//        return ApiResponse.<StaffResponse>builder()
//                .code(HttpStatus.OK.value())
//                .message("Successfully added staff")
//                .data(getStaffResponse())
//                .build();
//    }
//
////    @PutMapping
////    public ApiResponse<StaffResponse> updateStaff(@RequestBody StaffRequest staffRequest) {
////        return ApiResponse.<StaffResponse>builder()
////                .code(HttpStatus.OK.value())
////                .message("Successfully added staff")
////                .data(getStaffResponse())
////                .build();
////    }
}
