package org.example.rental.service

import org.example.rental.domain.Rental

interface RentalService {
    fun rentBook(
        userId: Long,
        bookId: Long,
        bookTitle: String,
    ): Rental

    fun returnBook(
        userId: Long,
        bookId: Long,
    ): Rental
}
