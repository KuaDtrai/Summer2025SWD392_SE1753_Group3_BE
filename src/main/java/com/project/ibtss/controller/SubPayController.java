package com.project.ibtss.controller;

import com.project.ibtss.dto.request.SubPayRequest;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.SubPayResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subPay")
public class SubPayController {
    private SubPayResponse getSubPayResponse() {
        return new SubPayResponse(1, "", 10f, "");
    }

    @GetMapping
    public ApiResponse<SubPayResponse> getSubPay() {
        return ApiResponse.<SubPayResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully get sub-pay")
                .data(getSubPayResponse()).build();
    }

    @GetMapping("/{paymentId}")
    public ApiResponse<List<SubPayResponse>> getSubPay(@PathVariable Integer paymentId) {
        List<SubPayResponse> subPayResponseList = new ArrayList<>();
        subPayResponseList.add(getSubPayResponse());
        subPayResponseList.add(getSubPayResponse());
        return ApiResponse.<List<SubPayResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully retrieved staff")
                .data(subPayResponseList)
                .build();
    }

    @PostMapping
    public ApiResponse<SubPayResponse> postSubPay(@RequestBody SubPayRequest subPayRequest) {
        return ApiResponse.<SubPayResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully get sub-pay")
                .data(getSubPayResponse()).build();    }

    @PutMapping("/{paymentId}")
    public ApiResponse<SubPayResponse> putSubPay(@PathVariable Integer paymentId, @RequestBody SubPayRequest subPayRequest) {
        return ApiResponse.<SubPayResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully get sub-pay")
                .data(getSubPayResponse()).build();    }
}
