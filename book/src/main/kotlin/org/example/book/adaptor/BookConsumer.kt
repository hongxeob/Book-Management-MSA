@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.example.book.adaptor

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.errors.WakeupException
import org.example.book.domain.event.StockChanged
import org.example.book.service.BookService
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

@Service
class BookConsumer(
    private val kafkaProperties: KafkaProperties,
    private val bookService: BookService,
) {
    private val log = LoggerFactory.getLogger(BookConsumer::class.java)
    private val closed = AtomicBoolean(false)
    private lateinit var kafkaConsumer: KafkaConsumer<String, String>

    private val executorService: ExecutorService = Executors.newCachedThreadPool()

    companion object {
        const val TOPIC: String = "topic_book"
    }

    @PostConstruct
    fun start() {
        log.info("Kafka consumer starting ...")
        val configs = kafkaProperties.properties
        this.kafkaConsumer = KafkaConsumer(configs as Map<String, Any>?)
        Runtime.getRuntime().addShutdownHook(Thread { this.shutdown() })
        kafkaConsumer!!.subscribe(Collections.singleton(TOPIC))
        log.info("Kafka consumer started")

        executorService.execute {
            try {
                while (!closed.get()) {
                    val records: ConsumerRecords<String, String> =
                        kafkaConsumer.poll(Duration.ofSeconds(3))
                    for (record in records) {
                        log.info("Consumed message in {} : {}", TOPIC, record.value())
                        val objectMapper = ObjectMapper()
                        val stockChanged: StockChanged =
                            objectMapper.readValue(record.value(), StockChanged::class.java)
                        bookService.processChangeBookState(stockChanged.bookId, stockChanged.bookStatus)
                    }
                }
                kafkaConsumer.commitSync()
            } catch (e: WakeupException) {
                if (!closed.get()) {
                    throw e
                }
            } catch (e: Exception) {
                log.error(e.message, e)
            } finally {
                log.info("kafka consumer close")
                kafkaConsumer.close()
            }
        }
    }

    fun getKafkaConsumer(): KafkaConsumer<String, String>? {
        return kafkaConsumer
    }

    fun shutdown() {
        log.info("Shutdown Kafka consumer")
        closed.set(true)
        kafkaConsumer.wakeup()
    }
}
