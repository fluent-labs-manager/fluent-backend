package org.fluent.usersservice.service

import org.fluent.core.exceptions.DoubleRecordException
import org.fluent.core.exceptions.NotFoundException
import org.fluent.usersservice.dto.user.CreateUserDto
import org.fluent.usersservice.dto.user.UpdateUserDto
import org.fluent.usersservice.dto.user.UserFilterDto
import org.fluent.usersservice.entity.User
import org.fluent.usersservice.repository.UserRepository
import org.fluent.usersservice.repository.mapper.UsersMapper
import org.fluent.usersservice.repository.spec.UsersSpecifications
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class UserService(
    private val repository: UserRepository,
    private val usersSpecifications: UsersSpecifications,
    private val usersMapper: UsersMapper,
    private val passwordEncoder: PasswordEncoder
) {

    fun getAll(
        filters: UserFilterDto?,
        pageable: Pageable,
    ): Page<User> {
        val specs = listOfNotNull(
            usersSpecifications.hasName(filters?.name),
            usersSpecifications.hasEmail(filters?.email)
        )

        return repository.findAll(Specification.allOf(specs), pageable)
    }

    fun getById(userId: UUID): User {
        return repository.findByIdOrNull(userId) ?: throw NotFoundException(
            message = "Пользователь с таким ID не найден"
        )
    }

    fun getByEmail(email: String): User {
        return repository.findByEmail(email) ?: throw NotFoundException(message = "")
    }

    @Transactional
    fun create(dto: CreateUserDto): User {
        repository.findByEmail(dto.email)
            ?.let { user -> throw DoubleRecordException(message = "Пользователь с email ${user.email} уже существует.") }

        return dto.let(usersMapper::userFromDto)
            .let(repository::save)
    }

    @Transactional
    fun update(id: UUID, dto: UpdateUserDto): User =
        getById(id).apply {
            dto.name?.let { name = it }
            dto.email?.let { email = it }
        }.let(repository::save)

    @Transactional
    fun delete(id: UUID): String {
        getById(id).let(repository::delete)
        return "Пользователь удален успешно."
    }

    @Transactional
    fun deleteAll(): String {
        repository.deleteAll()
        return "Все пользователи удалены."
    }
}