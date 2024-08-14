package org.example.book.web

import org.example.book.service.BookService
import org.example.book.web.rest.dto.BookInfoDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController("/api")
class BookResource(
    private val bookService: BookService,
) {
    @GetMapping("/books/bookInfo/{bookId}")
    fun findBookInfo(
        @PathVariable("bookId") bookId: Long,
    ): ResponseEntity<BookInfoDTO> {
        val book = bookService.findBookInfo(bookId)

        return ResponseEntity.ok(BookInfoDTO.from(book))
    }
}
