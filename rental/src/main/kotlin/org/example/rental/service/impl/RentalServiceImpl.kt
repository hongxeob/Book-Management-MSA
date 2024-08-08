package org.example.rental.service.impl

import org.example.rental.domain.Rental
import org.example.rental.exception.RentalException
import org.example.rental.repository.RentalRepository
import org.example.rental.service.RentalService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class RentalServiceImpl(
    private val rentalRepository: RentalRepository,
) : RentalService {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 대출
     * */
    @Transactional
    override fun rentBook(
        userId: Long,
        bookId: Long,
        bookTitle: String,
    ): Rental {
        log.info("=== 도서 대출 서비스 시작 ===")
        val rental =
            rentalRepository.findByUserId().orElseThrow {
                throw RentalException("사용자에 해당하는 대출이 없습니다.")
            }
        log.info("=== 찾은 Rental : $rental === ")
        rental.checkRentalAvailable()
        rental.rentBook(bookId, bookTitle)
        rentalRepository.save(rental)
        log.info("=== 대출 로직 완료 후 저장 완료 ===")

        /**
         * Todo 대출 후 이벤트 전송
         * 1. 도서 서비스에 재고 감소를 위해 도서 대출 이벤트 발송
         * 2. 도서 카탈로그 서비스에 대출된 도서로 상태를 변경하기 위한 이벤트 발송
         * 3. 대출로 인한 사용자 포인트 적립을 위해 사용자 서비스에 이벤트 발송
         * */

        return rental
    }

    @Transactional
    override fun returnBook(
        userId: Long,
        bookId: Long,
    ): Rental {
        log.info("=== 도서 반납 서비스 시작 ===")
        val rental = getRentalByUserId()
        rental.returnBook(bookId)
        rentalRepository.save(rental)

        /**
         * Todo 반납 후 이벤트 전송
         * 1. 도서 서비스에 재고 증가를 위해 도서 대출 이벤트 발송
         * 2. 도서 카탈로그 서비스에 반납된 도서로 상태를 변경하기 위한 이벤트 발송
         * */
        return rental
    }

    private fun getRentalByUserId(): Rental {
        return rentalRepository.findByUserId().orElseThrow {
            throw RentalException("사용자에 해당하는 대출이 없습니다.")
        }
    }
}
