package org.example.book.service

import org.example.book.domain.Book

interface BookService {
    fun processChangeBookState(
        bookId: Long,
        bookStatus: String,
    )

    fun findBookInfo(bookId: Long): Book

    fun registerNewBook(
        book: Book,
        inStockId: Long,
    ): Book

    fun updateBook(book: Book): Book

    fun deleteBook(bookId: Long)
}
