package com.project.ibtss.mapper;

import com.project.ibtss.dto.response.AccountResponse;
import com.project.ibtss.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "token", ignore = true)
    AccountResponse toAccountResponse(Account account);
}
