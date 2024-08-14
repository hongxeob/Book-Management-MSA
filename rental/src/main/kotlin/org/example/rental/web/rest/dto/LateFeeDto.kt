package org.example.rental.web.rest.dto

data class LateFeeDto(
    val userId: Long,
    val lateFee: Long,
)
