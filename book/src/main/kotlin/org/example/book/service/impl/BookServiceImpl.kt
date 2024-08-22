package org.example.book.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.book.domain.Book
import org.example.book.domain.event.BookCatalogEvent
import org.example.book.exception.BookException
import org.example.book.repository.BookRepository
import org.example.book.service.BookService
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class BookServiceImpl(
    private val bookRepository: BookRepository,
    private val inStockBookService: InStockBookServiceImpl,
    private val kafkaTemplate: KafkaTemplate<String, String>,
) : BookService {
    private val objectMapper = ObjectMapper()

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
        val event =
            BookCatalogEvent(
                eventType,
                bookId,
                LocalDateTime.now(),
            )
        val message = objectMapper.writeValueAsString(event)

        kafkaTemplate.send(eventType, message)
    }
}
