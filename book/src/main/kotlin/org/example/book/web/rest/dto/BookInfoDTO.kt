package org.example.book.web.rest.dto

import org.example.book.domain.Book
import org.example.book.domain.eumration.BookStatus
import org.example.book.domain.eumration.Classification
import org.example.book.domain.eumration.Location
import org.example.book.domain.vo.ISBN
import java.time.LocalDate

data class BookInfoDTO(
    val id: Long,
    val title: String,
    val author: String,
    val description: String,
) {
    companion object {
        fun toDTO(book: Book): BookInfoDTO =
            BookInfoDTO(
                book.id,
                book.title,
                book.author,
                book.description,
            )

        fun toEntity(bookInfoDTO: BookInfoDTO) =
            Book(
                bookInfoDTO.title,
                bookInfoDTO.description,
                bookInfoDTO.author,
                "KIM",
                ISBN(324234234234234L),
                LocalDate.now(),
                Classification.ARTS,
                BookStatus.AVAILABLE,
                Location.PUSAN,
            )
    }
}
