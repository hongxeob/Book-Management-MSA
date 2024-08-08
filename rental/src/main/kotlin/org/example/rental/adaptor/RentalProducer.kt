package org.example.rental.adaptor

interface RentalProducer {
    // 도서 서비스의 도서 상태 변경
    fun updateBookStatus(
        bookId: Long,
        bookStatus: String,
    )

    // 사용자 서비스의 포인트 적립
    fun savePoints(
        userId: Long,
        pointPerBooks: Int,
    )

    // 도서 카탈로그 서비스의 도서 상태 변경
    fun updateBookCatalogStatus(
        bookId: Long,
        eventTYpe: String,
    )
}
