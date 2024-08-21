package org.example.book.domain

import jakarta.persistence.*
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable

@Entity
@Table(name = "in_stock_books", schema = "public")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class InStockBook(
    title: String,
    description: String,
    author: String,
    publisher: String,
    isbn: ISBN,
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
    var isbn = isbn
        get() = isbn
        protected set
}
