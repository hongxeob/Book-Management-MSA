package org.example.book.domain.event

import java.time.LocalDateTime

data class BookCatalogEvent(
    val eventType: String,
    val bookId: Long,
    val createdAt: LocalDateTime,
)
