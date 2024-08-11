package org.example.book.adaptor

import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicBoolean

@Service
class BookConsumer() {
    val closed = AtomicBoolean()

    companion object {
        const val TOPIC = "topic_book"
    }
}
