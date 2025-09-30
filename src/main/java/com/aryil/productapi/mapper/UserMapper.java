package com.aryil.productapi.mapper;

import com.aryil.productapi.dto.UserDTO;
import com.aryil.productapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    UserDTO toDto(User user);
}
