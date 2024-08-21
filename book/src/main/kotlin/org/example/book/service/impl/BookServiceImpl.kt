package org.example.book.service.impl

import org.example.book.domain.Book
import org.example.book.exception.BookException
import org.example.book.repository.BookRepository
import org.example.book.service.BookService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BookServiceImpl(
    private val bookRepository: BookRepository,
    private val inStockBookService: InStockBookServiceImpl,
) : BookService {
    override fun processChangeBookState(
        bookId: Long,
        bookStatus: String,
    ) {
        // todo
    }

    override fun findBookInfo(bookId: Long): Book {
        val book =
            bookRepository.findById(bookId).orElseThrow {
                throw BookException("ID로 책을 찾을 수 없습니다.")
            }

        return book
    }

    @Transactional
    override fun registerNewBook(
        book: Book,
        inStockId: Long,
    ): Book {
        val newBook = bookRepository.save(book)
        inStockBookService.delete(inStockId)

        // todo code
        // sendBookCatalogEvent("NEW_BOOK", newBook.id)
    }

    @Transactional
    override fun updateBook(book: Book): Book {
    }

    @Transactional
    override fun deleteBook(bookId: Long) {
        TODO("Not yet implemented")
    }
}
