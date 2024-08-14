package org.example.rental.adaptor

import org.example.rental.web.rest.dto.LateFeeDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping

@FeignClient(name = "user")
interface UserClient {
    @PutMapping("/users/{userId}")
    fun usePoint(@PathVariable("userId") lateFeeDto: LateFeeDto)
}
