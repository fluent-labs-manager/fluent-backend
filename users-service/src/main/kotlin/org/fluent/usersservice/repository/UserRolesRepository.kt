package org.fluent.usersservice.repository

import org.fluent.usersservice.entity.UserRole
import org.fluent.usersservice.entity.UserRoleId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRolesRepository : JpaRepository<UserRole, UserRoleId>