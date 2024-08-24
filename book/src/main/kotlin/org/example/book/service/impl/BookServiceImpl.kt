package org.example.book.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.book.adaptor.BookProducer
import org.example.book.domain.Book
import org.example.book.domain.eumration.BookStatus
import org.example.book.domain.event.BookChangedEvent
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
    private val bookProducer: BookProducer,
) : BookService {
    private val objectMapper = ObjectMapper()

    @Transactional
    override fun processChangeBookState(
        bookId: Long,
        bookStatus: String,
    ) {
        val book = getBookId(bookId)
        book.updateStatus(BookStatus.valueOf(bookStatus))
    }

    override fun findBookInfo(bookId: Long): Book {
        val book = getBookId(bookId)

        return book
    }

    private fun getBookId(bookId: Long): Book {
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

        sendBookCatalogEvent("NEW_BOOK", newBook.id)

        return newBook
    }

    @Transactional
    override fun updateBook(book: Book): Book = book

    @Transactional
    override fun deleteBook(bookId: Long) {
        TODO("Not yet implemented")
    }

    private fun sendBookCatalogEvent(
        eventType: String,
        bookId: Long,
    ) {
        val book = getBookId(bookId)

        when (eventType) {
            "NEW_BOOK", "UPDATE_BOOK" -> {
                val bookChangedEvent =
                    BookChangedEvent.createTopicStatusIsCreate(book, eventType)
                bookProducer.sendBookCreateEvent(bookChangedEvent)
            }

            "DELETE_BOOK" -> {
                val bookChangedEvent = BookChangedEvent.createTopicStatusIsDelete(book, eventType)
                bookProducer.sendBookDeleteEvent(bookChangedEvent)
            }
        }
    }
}
