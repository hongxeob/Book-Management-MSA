package org.example.rental.web.rest.dto

import org.example.rental.domain.Rental
import org.example.rental.domain.RentalStatus

data class RentalDTO(
    val id: Long,
    val userId: Long,
    val rentalStatus: RentalStatus,
    val lateFee: Long,
) {
    companion object {
        fun from(rental: Rental): RentalDTO {
            return RentalDTO(
                id = rental.id,
                userId = rental.userId,
                rentalStatus = rental.rentalStatus,
                lateFee = rental.lateFee,
            )
        }
    }
}
