package org.fluent.usersservice.controller

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
    fun getByEmail(@PathVariable email: String): User = userService.getByEmail(email)

    @PostMapping
    fun create(dto: CreateUserDto): User = userService.create(dto)

    @PatchMapping("/{id}")
    fun update(@PathVariable id: UUID, dto: UpdateUserDto): User = userService.update(id, dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) = userService.delete(id)

    @DeleteMapping
    fun deleteAll() = userService.deleteAll()
}