package org.example.rental.web.rest.dto

import org.example.rental.domain.Rental
import org.example.rental.domain.enumration.RentalStatus

data class RentalDTO(
    val id: Long,
    val userId: Long,
    val rentalStatus: RentalStatus,
    val lateFee: Long,
) {
    companion object {
        fun from(rental: Rental): RentalDTO =
            RentalDTO(
                id = rental.id,
                userId = rental.userId,
                rentalStatus = rental.rentalStatus,
                lateFee = rental.lateFee,
            )
    }
}
