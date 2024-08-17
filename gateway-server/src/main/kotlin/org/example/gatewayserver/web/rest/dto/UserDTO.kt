package org.example.gatewayserver.web.rest.dto

import org.example.gatewayserver.domain.User

data class UserDTO(
    val id: Long,
) {
    companion object {
        fun from(user: User): UserDTO = UserDTO(user.id)
    }
}
