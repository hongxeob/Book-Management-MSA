package org.example.book.domain.event

import java.time.LocalDate

data class BookChanged(
    val title: String,
    val description: String,
    val author: String,
    val publicationDate: LocalDate,
    val classification: String,
    val rented: Boolean,
    val eventType: String,
    val rentCnt: Long,
    val bookId: Long,
)
