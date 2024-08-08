package org.example.rental.web.rest

import org.example.rental.adaptor.BookClient
import org.example.rental.service.RentalService
import org.example.rental.web.rest.dto.RentalDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class RentalResource(
    private val rentalService: RentalService,
    private val bookClient: BookClient,
) {
    @PostMapping("/rentals/{userId}/RentedItem/{book}")
    fun rentBook(
        @PathVariable("userId") userId: Long,
        @PathVariable("book") bookId: Long,
    ): ResponseEntity<RentalDTO> {
        /**
         * 도서 서비스를 호출해서 도서 정보 가져오기
         * */
        val bookInfoResult = bookClient.findBookInfo(bookId)
        val bookInfoDTO = bookInfoResult.body!!

        val rental = rentalService.rentBook(userId, bookInfoDTO.id, bookInfoDTO.title)

        return ResponseEntity.ok(RentalDTO.from(rental))
    }
}
