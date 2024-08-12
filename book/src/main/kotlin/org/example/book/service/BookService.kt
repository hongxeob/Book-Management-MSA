package org.example.book.service

interface BookService {
    fun processChangeBookState(
        bookId: Long,
        bookStatus: String,
    )
}
