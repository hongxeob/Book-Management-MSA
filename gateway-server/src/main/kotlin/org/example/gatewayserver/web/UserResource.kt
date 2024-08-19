package org.example.gatewayserver.web

import org.example.gatewayserver.service.UserService
import org.example.gatewayserver.web.rest.dto.LateFeeDTO
import org.example.gatewayserver.web.rest.dto.UserDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/users")
@RestController
class UserResource(
    private val userService: UserService,
) {
    @PutMapping("/late-fee")
    fun usePoint(
        @RequestBody lateFeeDTO: LateFeeDTO,
    ): ResponseEntity<UserDTO> {
        val userDTO = userService.usePoints(lateFeeDTO.userId, lateFeeDTO.point)

        return ResponseEntity.ok(userDTO)
    }
}
