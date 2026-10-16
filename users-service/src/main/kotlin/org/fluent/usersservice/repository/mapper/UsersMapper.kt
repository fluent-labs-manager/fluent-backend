package org.fluent.usersservice.repository.mapper

import org.fluent.core.dto.io.UserDto
import org.fluent.usersservice.dto.user.CreateUserDto
import org.fluent.usersservice.entity.User
import org.mapstruct.AfterMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget

@Mapper(componentModel = "spring")
interface UsersMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    fun userFromDto(dto: CreateUserDto): User

    fun userDtoFromUser(user: User): UserDto =
        UserDto(
            id = requireNotNull(user.id) { "Cannot map a user without an ID" },
            name = user.name,
            email = user.email,
            roles = user.roles.mapNotNull { it.id.role }.toSet(),
        )

    @AfterMapping
    fun initializeRelations(@MappingTarget user: User) {
        user.roles = mutableSetOf()
    }
}
