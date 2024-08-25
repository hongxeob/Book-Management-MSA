package org.example.book.service

import org.example.book.domain.Book
import org.example.book.web.rest.dto.BookInfoDTO

interface BookService {
    fun processChangeBookState(
        bookId: Long,
        bookStatus: String,
    )

    fun findBookInfo(bookId: Long): Book

    fun registerNewBook(
        bookRequest: BookInfoDTO,
        inStockId: Long,
    ): Book

    fun updateBook(bookId: Long): Book

    fun deleteBook(bookId: Long)
}
