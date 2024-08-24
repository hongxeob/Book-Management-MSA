package org.example.book.adaptor.impl

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.book.adaptor.BookProducer
import org.example.book.domain.event.BookChangedEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class BookProducerImpl(
    private val kafkaTemplate: KafkaTemplate<String, String>,
) : BookProducer {
    private val log = LoggerFactory.getLogger(this::class.java)

    // 토픽명
    companion object {
        private const val TOPIC_BOOK = "topic_book"
        private const val TOPIC_CATALOG = "topic_catalog"
        private const val TOPIC_POINT = "topic_point"
    }

    private val objectMapper = ObjectMapper()

    override fun sendBookCreateEvent(bookChangedEvent: BookChangedEvent) {
        val message = objectMapper.writeValueAsString(bookChangedEvent)
        kafkaTemplate.send(message, TOPIC_CATALOG)
    }

    override fun sendBookDeleteEvent(bookChangedEvent: BookChangedEvent) {
        val message = objectMapper.writeValueAsString(bookChangedEvent)
        kafkaTemplate.send(message, TOPIC_CATALOG)
    }
}
