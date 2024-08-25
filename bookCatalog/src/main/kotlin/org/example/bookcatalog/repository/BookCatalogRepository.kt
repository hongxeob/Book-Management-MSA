package org.example.bookcatalog.repository

import org.example.bookcatalog.domain.BookCatalog
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface BookCatalogRepository : MongoRepository<BookCatalog, String> {
    fun findByTitleContaining(
        title: String,
        pageable: Pageable,
    ): Page<BookCatalog>

    fun findTop10ByOrderByRentCntDesc(): List<BookCatalog>
}
