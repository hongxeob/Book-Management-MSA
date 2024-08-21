@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.example.book.domain

import jakarta.persistence.*
import org.example.book.domain.eumration.BookStatus
import org.example.book.domain.eumration.Classification
import org.example.book.domain.eumration.Location
import org.example.book.domain.vo.ISBN
import java.io.Serializable
import java.time.LocalDate

@Entity
@Table(name = "book")
class Book(
    title: String,
    description: String,
    author: String,
    publisher: String,
    isbn: ISBN,
    publicationDate: LocalDate,
    classification: Classification,
    bookStatus: BookStatus,
    location: Location,
) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(name = "title")
    var title = title
        get() = title
        protected set

    @Column(name = "description")
    var description = description
        get() = description
        protected set

    @Column(name = "author")
    var author = author
        get() = author
        protected set

    @Column(name = "publisher")
    var publisher = publisher
        get() = publisher
        protected set

    @Embedded
    @Column(name = "isbn")
    var isbn = isbn
        get() = isbn
        protected set

    @Column(name = "publication_date")
    var publicationDate = publicationDate
        get() = publicationDate
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "classification")
    var classification = classification
        get() = classification
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "book_status")
    var bookStatus = bookStatus
        get() = bookStatus
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "location")
    var location = location
        get() = location
        protected set
}
