package com.project.ibtss.dto.request;

import lombok.Getter;

@Getter
public class SubPayRequest {
    private Integer paymentId;
    private String type;
    private Float amount;
    private String description;
}
