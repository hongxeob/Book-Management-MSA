package org.example.gatewayserver.service

import org.example.gatewayserver.web.rest.dto.UserDTO

interface UserService {
    fun updateUser(userDTO: UserDTO): UserDTO

    fun savePoints(
        userId: Long,
        point: Int,
    ): UserDTO

    fun usePoints(
        userId: Long,
        point: Int,
    ): UserDTO
}
