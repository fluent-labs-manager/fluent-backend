package org.fluent.usersservice.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import org.fluent.core.api.BasicSuccessfulResponse
import org.fluent.core.enums.UserRoles
import org.fluent.usersservice.dto.user.CreateUserDto
import org.fluent.usersservice.dto.user.UpdateUserDto
import org.fluent.usersservice.dto.user.UserFilterDto
import org.fluent.usersservice.entity.User
import org.fluent.usersservice.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAll(
        @ModelAttribute filters: UserFilterDto,
        pageable: Pageable
    ): Page<User> = userService.getAll(filters, pageable)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): User = userService.getById(id)

    @GetMapping("/getByEmail/{email}")
    fun getByEmail(
        @Email(message = "Некорректный email")
        @PathVariable
        email: String
    ): User = userService.getByEmail(email)

    @PostMapping
    fun create(@Valid @RequestBody dto: CreateUserDto): User = userService.create(dto)

    @PatchMapping("/{id}")
    fun update(@PathVariable id: UUID, @Valid @RequestBody dto: UpdateUserDto): User = userService.update(id, dto)

    @PatchMapping("/addRole/{id}/{role}")
    fun addRole(@PathVariable id: UUID, @PathVariable role: UserRoles): User = userService.addRole(id, role)

    @PatchMapping("/removeRole/{id}/{role}")
    fun removeRole(@PathVariable id: UUID, @PathVariable role: UserRoles): User = userService.removeRole(id, role)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) = BasicSuccessfulResponse(userService.delete(id))

    @DeleteMapping
    fun deleteAll() = BasicSuccessfulResponse(userService.deleteAll())
}