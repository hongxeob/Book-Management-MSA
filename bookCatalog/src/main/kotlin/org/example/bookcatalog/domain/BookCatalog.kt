@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.example.bookcatalog.domain

import org.example.bookcatalog.domain.event.BookChanged
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Document(value = "catalogs")
class BookCatalog(
    title: String,
    description: String,
    author: String,
    publicationDate: LocalDate,
    classification: String,
    rented: Boolean,
    rentCnt: Long,
    bookId: Long,
) {
    @Id
    val id: String = UUID.randomUUID().toString()

    @Field("title")
    var title: String = title
        protected set

    @Field("description")
    var description: String = description
        protected set

    @Field("author")
    var author: String = author
        protected set

    @Field("publication_date")
    var publicationDate: LocalDate = publicationDate
        protected set

    @Field("classification")
    var classification: String = classification
        protected set

    @Field("rented")
    var rented: Boolean = rented
        protected set

    @Field("rent_cnt")
    var rentCnt: Long = rentCnt
        protected set

    @Field("book_id")
    var bookId: Long = bookId
        protected set

    companion object {
        fun registerNewBookCatalog(bookChanged: BookChanged): BookCatalog =
            BookCatalog(
                bookChanged.title,
                bookChanged.description,
                bookChanged.author,
                LocalDate.parse(
                    bookChanged.publicationDate,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                ),
                bookChanged.classification,
                bookChanged.rented,
                bookChanged.rentCnt,
                bookChanged.bookId,
            )
    }

    fun rentBook(): BookCatalog {
        this.rentCnt += 1
        this.rented = true
        return this
    }

    fun returnBook(): BookCatalog {
        this.rented = false
        return this
    }

    fun updateBookCatalogInfo(bookChanged: BookChanged) {
    }
}
