package org.example.bookcatalog.web

import org.example.bookcatalog.domain.BookCatalog
import org.example.bookcatalog.service.BookCatalogService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(("/api/book-catalogs"))
class BookCatalogResource(
    private val bookCatalogService: BookCatalogService,
) {
    @GetMapping("/top-10")
    fun loadTop10Books(): ResponseEntity<List<BookCatalog>> {
        val response = bookCatalogService.loadTop10Books()

        return ResponseEntity.ok(response)
    }
}
