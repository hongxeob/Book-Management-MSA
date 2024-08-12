package org.example.book.domain.event

data class StockChanged(
    val bookId: Long,
    val bookStatus: String,
)
