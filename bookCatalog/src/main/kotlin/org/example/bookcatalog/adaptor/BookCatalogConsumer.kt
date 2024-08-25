package org.example.bookcatalog.adaptor

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.errors.WakeupException
import org.example.bookcatalog.domain.event.BookChanged
import org.example.bookcatalog.service.BookCatalogService
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

@Service
class BookCatalogConsumer(
    private val kafkaProperties: KafkaProperties,
    private val bookCatalogService: BookCatalogService,
) {
    private val log = LoggerFactory.getLogger(BookCatalogConsumer::class.java)
    private val closed = AtomicBoolean(false)
    private lateinit var kafkaConsumer: KafkaConsumer<String, String>

    private val executorService: ExecutorService = Executors.newCachedThreadPool()

    companion object {
        const val TOPIC: String = "topic_bookCatalog"
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
                        // 도서 정보 변경됨 도메인 이벤트 생성
                        val bookChanged: BookChanged =
                            objectMapper.readValue(record.value(), BookChanged::class.java)

                        // 도서 카탈로그 변경 프로세스 진행
                        bookCatalogService.processCatalogChanged(bookChanged)
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

    fun shutdown() {
        log.info("Shutdown Kafka consumer")
        closed.set(true)
        kafkaConsumer.wakeup()
    }
}
