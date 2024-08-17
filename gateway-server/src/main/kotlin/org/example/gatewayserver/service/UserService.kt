package org.example.gatewayserver.service

import org.example.gatewayserver.web.rest.dto.UserDTO

interface UserService {
    fun updateUser(userDTO: UserDTO): UserDTO
}
