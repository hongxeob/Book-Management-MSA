package org.example.gatewayserver.repository

import org.example.gatewayserver.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>
