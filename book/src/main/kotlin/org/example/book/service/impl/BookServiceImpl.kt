package org.example.book.service.impl

import org.example.book.service.BookService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BookServiceImpl() : BookService {
    override fun processChangeBookState(
        bookId: Long,
        bookStatus: String,
    ) {
        // todo
    }
}
