package org.fluent.usersservice.service

import org.fluent.core.dto.io.UserDto
import org.fluent.core.enums.UserRoles
import org.fluent.core.exceptions.DoubleRecordException
import org.fluent.core.exceptions.NotFoundException
import org.fluent.usersservice.dto.user.CreateUserDto
import org.fluent.usersservice.dto.user.DeletedUser
import org.fluent.usersservice.dto.user.UpdateUserDto
import org.fluent.usersservice.dto.user.UserFilterDto
import org.fluent.usersservice.entity.UserRoleId
import org.fluent.usersservice.repository.UserRepository
import org.fluent.usersservice.repository.UserRolesRepository
import org.fluent.usersservice.repository.mapper.UserRoleMapper
import org.fluent.usersservice.repository.mapper.UsersMapper
import org.fluent.usersservice.repository.spec.UsersSpecifications
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
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
    private val userRolesRepository: UserRolesRepository,
    private val usersSpecifications: UsersSpecifications,
    private val usersMapper: UsersMapper,
    private val userRoleMapper: UserRoleMapper,
    private val passwordEncoder: PasswordEncoder
) {

    fun getAll(
        filters: UserFilterDto?,
        pageable: Pageable,
    ): Page<UserDto> {
        val specs = listOfNotNull(
            usersSpecifications.hasName(filters?.name),
            usersSpecifications.hasEmail(filters?.email)
        )

        return repository.findAll(Specification.allOf(specs), pageable)
            .map(usersMapper::userDtoFromUser)
    }

    @Cacheable(cacheNames = ["userByUUID"], key = "#userId")
    fun getById(userId: UUID): UserDto {
        return repository.findByIdOrNull(userId)
            ?.let(usersMapper::userDtoFromUser)
            ?: throw NotFoundException(message = "Пользователь с таким ID не найден")
    }

    @Cacheable(cacheNames = ["userByEmail"], key = "#email")
    fun getByEmail(email: String): UserDto {
        return repository.findByEmail(email)
            ?.let(usersMapper::userDtoFromUser)
            ?: throw NotFoundException(message = "Пользователь с таким email не найден")
    }

    @Transactional
    @Caching(
        cacheable = [
            Cacheable(cacheNames = ["userByUUID"], key = "#result.id"),
            Cacheable(cacheNames = ["userByEmail"], key = "#result.email"),
        ]
    )
    fun create(dto: CreateUserDto): UserDto {
        repository.findByEmail(dto.email)
            ?.let { user -> throw DoubleRecordException(message = "Пользователь с email ${user.email} уже существует.") }

        return dto.copy(password = passwordEncoder.encode(dto.password))
            .let(usersMapper::userFromDto)
            .let(repository::saveAndFlush)
            .let { user ->
                user.let { userRoleMapper.userRoleFromUserAndRole(user, dto.role ?: UserRoles.STUDENT) }
                    .let(userRolesRepository::save)
                    .let(user.roles::add)

                return@let usersMapper.userDtoFromUser(user)
            }
    }

    @Transactional
    @Caching(
        evict = [
            CacheEvict(cacheNames = ["userByEmail"], key = "#result.email"),
            CacheEvict(cacheNames = ["userByUUID"], key = "#result.id"),
        ]
    )
    fun update(userId: UUID, dto: UpdateUserDto): UserDto {
        val user = repository.findByIdOrNull(userId)
            ?: throw NotFoundException(message = "Пользователь с таким ID не найден")

        return user.apply {
            dto.name?.let { name = it }
            dto.email?.let { newEmail ->
                val oldUser = repository.findByEmail(newEmail)
                if (oldUser != null && oldUser != this)
                    throw DoubleRecordException("Пользователь с email $newEmail уже существует.")

                email = newEmail
            }
        }.let { user ->
            repository.save(user)
            return@let usersMapper.userDtoFromUser(user)
        }
    }

    @Transactional
    @Caching(
        evict = [
            CacheEvict(cacheNames = ["userByEmail"], key = "#result.email"),
            CacheEvict(cacheNames = ["userByUUID"], key = "#result.id"),
        ]
    )
    fun addRole(userId: UUID, role: UserRoles): UserDto =
        repository.findByIdOrNull(userId)
            ?.let { user ->
                userRoleMapper.userRoleFromUserAndRole(user, role)
                    .let(userRolesRepository::save)
                    .let(user.roles::add)

                return@let usersMapper.userDtoFromUser(user)
            }
            ?: throw NotFoundException(message = "Пользователь с таким ID не найден")

    @Transactional
    @Caching(
        evict = [
            CacheEvict(cacheNames = ["userByEmail"], key = "#result.email"),
            CacheEvict(cacheNames = ["userByUUID"], key = "#result.id"),
        ]
    )
    fun removeRole(userId: UUID, role: UserRoles): UserDto =
        repository.findByIdOrNull(userId)
            ?.let { user ->
                userRolesRepository.deleteById(UserRoleId(userId = userId, role = role))
                return@let usersMapper.userDtoFromUser(user)
            }
            ?: throw NotFoundException(message = "Пользователь с таким ID не найден")

    @Transactional
    @Caching(
        evict = [
            CacheEvict(cacheNames = ["userByEmail"], key = "#result.email"),
            CacheEvict(cacheNames = ["userByUUID"], key = "#result.id"),
        ]
    )
    fun delete(userId: UUID): DeletedUser {
        val user = repository.findByIdOrNull(userId)
            ?: throw NotFoundException("Пользователь с таким ID не найден")

        val deletedUser = DeletedUser(
            id = user.id!!,
            email = user.email,
        )

        repository.delete(user)

        return deletedUser
    }

    @Transactional
    @Caching(
        evict = [
            CacheEvict(cacheNames = ["userByEmail"], allEntries = true),
            CacheEvict(cacheNames = ["userByUUID"], allEntries = true),
        ]
    )
    fun deleteAll(): String {
        repository.deleteAll()
        return "Все пользователи удалены."
    }
}
