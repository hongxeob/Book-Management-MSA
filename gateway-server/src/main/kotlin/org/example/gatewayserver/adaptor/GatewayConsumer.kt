package org.example.gatewayserver.adaptor

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.example.gatewayserver.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicBoolean

@Component
class GatewayConsumer(
    private val kafkaProperties: KafkaProperties,
    private val userService: UserService,
) {
    private val logger = LoggerFactory.getLogger(GatewayConsumer::class.java)
    private val closed = AtomicBoolean(false)
    private lateinit var kafkaConsumer: KafkaConsumer<String, String>
}
