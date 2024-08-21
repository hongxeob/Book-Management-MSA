package org.example.gatewayserver.service.impl

import org.example.gatewayserver.domain.User
import org.example.gatewayserver.exception.UserException
import org.example.gatewayserver.repository.UserRepository
import org.example.gatewayserver.service.UserService
import org.example.gatewayserver.web.rest.dto.UserDTO
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {
    @Transactional
    override fun updateUser(userDTO: UserDTO): UserDTO {
        val user = getUserById(userDTO.id)

        user.updateUserinfo()

        return UserDTO.from(user)
    }

    private fun getUserById(userId: Long): User =
        userRepository.findById(userId).orElseThrow {
            throw UserException("ID로 유저를 찾을 수 없습니다.")
        }

    @Transactional
    override fun savePoints(
        userId: Long,
        point: Int,
    ): UserDTO {
        val user = getUserById(userId)
        user.savePoints(point)

        return UserDTO.from(user)
    }

    @Transactional
    override fun usePoints(
        userId: Long,
        point: Int,
    ): UserDTO {
        val user = getUserById(userId)
        user.usePoints(point)

        return UserDTO.from(user)
    }
}
