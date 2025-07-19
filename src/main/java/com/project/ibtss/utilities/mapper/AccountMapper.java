package com.project.ibtss.utilities.mapper;

import com.project.ibtss.dto.response.DriverResponse;
import com.project.ibtss.dto.response.AccountManageResponse;
import com.project.ibtss.dto.response.AccountResponse;
import com.project.ibtss.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountResponse toAccountResponse(Account account);
    DriverResponse toDriverResponse(Account account);
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "address", ignore = true)
    AccountManageResponse toAccountManageResponse(Account account);
}
