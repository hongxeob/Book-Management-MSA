package org.example.book.domain

import jakarta.persistence.*
import org.example.book.domain.eumration.Source
import org.example.book.domain.vo.ISBN
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.time.LocalDate

@Entity
@Table(name = "in_stock_books", schema = "public")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class InStockBook(
    title: String,
    description: String,
    author: String,
    publisher: String,
    isbn: ISBN,
    publicationDate: LocalDate,
    source: Source,
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
    @Column(name = "source")
    var source = source
        get() = source
        protected set
}
