package org.example.bookcatalog.service.impl

import org.example.bookcatalog.domain.BookCatalog
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
}
