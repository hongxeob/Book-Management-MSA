package org.example.bookcatalog.domain.event

data class BookChanged(
    val title: String,
    val description: String,
    val author: String,
    val publicationDate: String, // Assuming it's in "yyyy-MM-dd" format
    val classification: String,
    val rented: Boolean,
    val rentCnt: Long,
    val bookId: Long,
    val eventType: String,
)
