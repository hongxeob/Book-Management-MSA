package org.example.book.domain.vo

import jakarta.persistence.Embeddable

@Embeddable
class ISBN(
    val isbn: Long,
) {
    init {
        if (!isValidISBN(isbn)) {
            throw IllegalArgumentException("Invalid ISBN: $isbn")
        }
    }

    private fun isValidISBN(isbn: Long): Boolean {
        if (isbn.toString().length != 13) {
            return false
        }
        return getCheckSum(isbn) % 10 == 0
    }

    private fun getCheckSum(isbn: Long): Int = isbn.toInt()
}
