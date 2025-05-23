package com.maxim.webrise.controller.mapper;

import com.maxim.webrise.controller.dto.ReadUserDto;
import com.maxim.webrise.controller.dto.UserDto;
import com.maxim.webrise.repository.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);
    ReadUserDto toDto(User user);
}
