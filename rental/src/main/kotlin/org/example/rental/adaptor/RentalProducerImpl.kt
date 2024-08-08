@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.example.rental.adaptor

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.producer.ProducerRecord
import org.example.rental.domain.event.BookCatalogChanged
import org.example.rental.domain.event.PointChanged
import org.example.rental.domain.event.StockChanged
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class RentalProducerImpl(
    private val kafkaTemplate: KafkaTemplate<String, String>,
) : RentalProducer {
    private val log = LoggerFactory.getLogger(this::class.java)

    // 토픽명
    companion object {
        private const val TOPIC_BOOK = "topic_book"
        private const val TOPIC_CATALOG = "topic_catalog"
        private const val TOPIC_POINT = "topic_point"
    }

    private val objectMapper = ObjectMapper()

    override fun updateBookStatus(
        bookId: Long,
        bookStatus: String,
    ) {
        val stockChanged = StockChanged(bookId, bookStatus)
        val message = objectMapper.writeValueAsString(stockChanged)
        kafkaTemplate.send(ProducerRecord(TOPIC_BOOK, message)).get()
    }

    // 사용자 서비스의 포인트 적립에 대한 카프카 메시지 발행
    override fun savePoints(
        userId: Long,
        pointPerBooks: Int,
    ) {
        val pointChanged = PointChanged(userId, pointPerBooks)
        val message = objectMapper.writeValueAsString(pointChanged)
        kafkaTemplate.send(ProducerRecord(TOPIC_POINT, message)).get()
    }

    override fun updateBookCatalogStatus(
        bookId: Long,
        eventType: String,
    ) {
        val bookCatalogChanged = BookCatalogChanged(bookId, eventType)
        val message = objectMapper.writeValueAsString(bookCatalogChanged)
        kafkaTemplate.send(ProducerRecord(TOPIC_CATALOG, message)).get()
    }
}
