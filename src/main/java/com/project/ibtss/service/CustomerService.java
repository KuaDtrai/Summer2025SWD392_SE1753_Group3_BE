package com.project.ibtss.service;

import com.project.ibtss.dto.request.CustomerRequest;
import com.project.ibtss.dto.response.CustomerResponse;

public interface CustomerService{
    CustomerResponse updateCustomer(CustomerRequest request);
    CustomerResponse getCustomer();
}
