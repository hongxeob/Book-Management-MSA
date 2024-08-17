package org.example.gatewayserver.service.impl

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
        val user =
            userRepository.findById(userDTO.id).orElseThrow {
                throw UserException("ID로 유저를 찾을 수 없습니다.")
            }

        user.updateUserinfo()

        return UserDTO.from(user)
    }
}
