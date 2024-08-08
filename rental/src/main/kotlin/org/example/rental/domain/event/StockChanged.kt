package org.example.rental.domain.event

data class StockChanged(
    val bookId: Long,
    val bookStatus: String,
)
