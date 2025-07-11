package com.project.ibtss.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class StationRequest {
    @Valid
    @Size(max = 50)
    String name;
    @Valid
            @Size(max = 50)
    String address;
}
