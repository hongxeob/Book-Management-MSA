package org.example.bookcatalog.service.impl

import org.example.bookcatalog.domain.BookCatalog
import org.example.bookcatalog.domain.event.BookChanged
import org.example.bookcatalog.repository.BookCatalogRepository
import org.example.bookcatalog.service.BookCatalogService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BookCatalogServiceImpl(
    private val bookCatalogRepository: BookCatalogRepository,
) : BookCatalogService {
    override fun findBookByTitle(
        title: String,
        pageable: Pageable,
    ): Page<BookCatalog> = bookCatalogRepository.findByTitleContaining(title, pageable)

    override fun loadTop10Books(): List<BookCatalog> = bookCatalogRepository.findTop10ByOrderByRentCntDesc()

    @Transactional
    fun deleteBook(bookChanged: BookChanged) {
        bookCatalogRepository.deleteByBookId(bookChanged.bookId)
    }

    @Transactional
    fun registerNewBook(bookChanged: BookChanged): BookCatalog {
        val bookCatalog = BookCatalog.registerNewBookCatalog(bookChanged)
        return bookCatalogRepository.save(bookCatalog)
    }

    @Transactional
    fun updateBookStatus(bookChanged: BookChanged) {
        val bookCatalog = bookCatalogRepository.findByBookId(bookChanged.bookId)

        when (bookChanged.eventType) {
            "RENT_BOOK" -> {
                bookCatalog.rentBook()
            }

            "RETURN_BOOK" -> {
                bookCatalog.returnBook()
            }
        }
    }

    @Transactional
    fun updateBookInfo(bookChanged: BookChanged) {
        val bookCatalog = bookCatalogRepository.findByBookId(bookChanged.bookId)
        bookCatalog.updateBookCatalogInfo(bookChanged)
    }

    override fun processCatalogChanged(bookChanged: BookChanged) {
        val eventType = bookChanged.eventType
        when (eventType) {
            "NEW_BOOK" -> {
                registerNewBook(bookChanged)
            }

            "DELETE_BOOK" -> {
                deleteBook(bookChanged)
            }

            "RENT_BOOK", "RETURN_BOOK" -> {
                updateBookStatus(bookChanged)
            }

            "UPDATE_BOOK" -> {
                updateBookInfo(bookChanged)
            }
        }
    }
}
