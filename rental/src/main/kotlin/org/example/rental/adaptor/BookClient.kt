package org.example.rental.adaptor

import org.example.rental.web.rest.dto.BookInfoDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "book")
interface BookClient {
    @GetMapping("/api/books/bookInfo/{bookId}")
    fun findBookInfo(
        @PathVariable("bookId") bookId: Long,
    ): ResponseEntity<BookInfoDTO>
}
