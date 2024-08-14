@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.example.rental.web.rest

import feign.FeignException
import org.example.rental.adaptor.BookClient
import org.example.rental.service.RentalService
import org.example.rental.web.rest.dto.LateFeeDto
import org.example.rental.web.rest.dto.RentalDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class RentalResource(
    private val rentalService: RentalService,
    private val bookClient: BookClient,
) {
    // 도서 대출 API
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

    // 도서 반납 API
    @DeleteMapping("/rentals/{userId}/RentedItem/{book}")
    fun returnBook(
        @PathVariable("userId") userId: Long,
        @PathVariable("book") bookId: Long,
    ): ResponseEntity<RentalDTO> {
        val returnBook = rentalService.returnBook(userId, bookId)

        return ResponseEntity.ok(RentalDTO.from(returnBook))
    }

    @PutMapping("/rentals/release-overdue/user/{userId}")
    fun releaseOverdueBook(
        @PathVariable("userId") userId: Long,
    ): ResponseEntity<RentalDTO> {
        val lateFee = rentalService.findLateFee(userId)
        val lateFeeDto = LateFeeDto(userId, lateFee)

        try {
            userClinet.usePoint(lateFeeDto)
        } catch (e: FeignException.FeignClientException) {
            e.printStackTrace()
        }
        val rental = rentalService.releaseOverdueBook(userId)

        return ResponseEntity.ok(RentalDTO.from(rental))
    }
}
