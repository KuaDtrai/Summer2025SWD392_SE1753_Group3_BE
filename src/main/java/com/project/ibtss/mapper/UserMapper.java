package com.project.ibtss.mapper;

import com.project.ibtss.dto.response.UserResponse;
import com.project.ibtss.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "token", ignore = true)
    UserResponse toUserResponse(User user);
}
