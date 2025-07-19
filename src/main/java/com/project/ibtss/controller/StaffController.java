package com.project.ibtss.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
