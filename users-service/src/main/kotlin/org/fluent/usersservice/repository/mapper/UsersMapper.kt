package org.fluent.usersservice.repository.mapper

import org.fluent.usersservice.dto.user.CreateUserDto
import org.fluent.usersservice.entity.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface UsersMapper {
    @Mapping(target = "id", ignore = true)
    fun userFromDto(dto: CreateUserDto): User
}