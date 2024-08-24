package org.example.book.adaptor

import org.example.book.domain.event.BookChangedEvent

interface BookProducer {
    fun sendBookCreateEvent(bookChangedEvent: BookChangedEvent)

    fun sendBookDeleteEvent(bookChangedEvent: BookChangedEvent)
}
