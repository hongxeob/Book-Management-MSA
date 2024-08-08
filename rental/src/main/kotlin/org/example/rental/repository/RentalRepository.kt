package org.example.rental.repository

import org.example.rental.domain.Rental
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface RentalRepository : JpaRepository<Rental, Long> {
    fun findByUserId(): Optional<Rental>
}
