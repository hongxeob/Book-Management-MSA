package org.example.bookcatalog.service

import org.example.bookcatalog.domain.BookCatalog
import org.example.bookcatalog.domain.event.BookChanged
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BookCatalogService {
    fun findBookByTitle(
        title: String,
        pageable: Pageable,
    ): Page<BookCatalog>

    fun loadTop10Books(): List<BookCatalog>

    fun processCatalogChanged(bookChanged: BookChanged)
}
