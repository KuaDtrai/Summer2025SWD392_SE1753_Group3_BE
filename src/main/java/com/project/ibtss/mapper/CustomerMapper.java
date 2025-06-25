package com.project.ibtss.mapper;

import com.project.ibtss.dto.response.CustomerResponse;
import com.project.ibtss.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "fullName", source = "account.fullName")
    @Mapping(target = "email", source = "account.email")
    @Mapping(target = "phone", source = "account.phone")
    CustomerResponse toCustomer(Customer customer);
}
