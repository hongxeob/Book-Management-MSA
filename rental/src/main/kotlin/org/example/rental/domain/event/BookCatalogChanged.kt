package org.example.rental.domain.event

data class BookCatalogChanged(
    val bookId: Long,
    val eventType: String,
)
