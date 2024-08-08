package org.example.rental.domain.event

data class PointChanged(
    val userId: Long,
    val points: Int,
)
