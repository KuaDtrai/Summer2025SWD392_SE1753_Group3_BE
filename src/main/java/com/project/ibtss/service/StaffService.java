package com.project.ibtss.service;

import com.project.ibtss.dto.request.StaffRequest;
import com.project.ibtss.dto.response.StaffResponse;

public interface StaffService {
    StaffResponse updateStaff(StaffRequest request);
    StaffResponse getStaff();
}
