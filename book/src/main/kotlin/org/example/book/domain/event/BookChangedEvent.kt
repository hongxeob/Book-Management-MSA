package org.example.book.domain.event

import org.example.book.domain.Book
import org.example.book.domain.eumration.BookStatus
import java.time.LocalDate

data class BookChangedEvent(
    val title: String?,
    val description: String?,
    val author: String?,
    val publicationDate: LocalDate?,
    val classification: String?,
    val rented: Boolean?,
    val eventType: String,
    val rentCnt: Long?,
    val bookId: Long,
) {
    companion object {
        fun createTopicStatusIsCreate(
            book: Book,
            eventType: String,
        ): BookChangedEvent =
            BookChangedEvent(
                book.title,
                book.description,
                book.author,
                book.publicationDate,
                book.classification.name,
                book.bookStatus == BookStatus.AVAILABLE,
                eventType,
                0L,
                book.id,
            )

        fun createTopicStatusIsDelete(
            book: Book,
            eventType: String,
        ): BookChangedEvent =
            BookChangedEvent(
                null,
                null,
                null,
                null,
                null,
                null,
                eventType,
                null,
                book.id,
            )
    }
}
