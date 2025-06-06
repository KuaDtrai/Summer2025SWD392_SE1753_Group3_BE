package com.project.ibtss.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ListAccountResponse {
    List<AccountResponse> accountResponses;
}
