@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.example.book.web

import org.example.book.service.BookService
import org.example.book.web.rest.dto.BookInfoDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController("/api/books")
class BookResource(
    private val bookService: BookService,
) {
    @GetMapping("/bookInfo/{bookId}")
    fun findBookInfo(
        @PathVariable("bookId") bookId: Long,
    ): ResponseEntity<BookInfoDTO> {
        val book = bookService.findBookInfo(bookId)

        return ResponseEntity.ok(BookInfoDTO.toDTO(book))
    }

    @PostMapping("/{inStockId}")
    fun registerBook(
        @PathVariable inStockId: Long,
        @RequestBody request: BookInfoDTO,
    ): ResponseEntity<BookInfoDTO> {
        val response = bookService.registerNewBook(request, inStockId)

        return ResponseEntity.ok(BookInfoDTO.toDTO(response))
    }
}
