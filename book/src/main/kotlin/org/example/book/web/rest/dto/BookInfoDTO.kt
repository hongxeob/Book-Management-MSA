package org.example.book.web.rest.dto

import org.example.book.domain.Book

data class BookInfoDTO(
    val id: Long,
    val title: String,
    val author: String,
    val description: String,
) {
    companion object {
        fun from(book: Book): BookInfoDTO =
            BookInfoDTO(
                book.id,
                book.title,
                book.author,
                book.description,
            )
    }
}
