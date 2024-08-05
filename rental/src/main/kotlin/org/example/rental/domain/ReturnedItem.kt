@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.example.rental.domain

import jakarta.persistence.*
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.time.LocalDate

@Entity
@Table(name = "returned_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class ReturnedItem(
    bookId: Long,
    bookTitle: String,
    returnedDate: LocalDate,
) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(name = "book_id")
    var bookId: Long = bookId
        protected set

    @Column(name = "book_title")
    var bookTitle: String = bookTitle
        protected set

    @Column(name = "rented_date")
    var returnedDate: LocalDate = returnedDate
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var rental: Rental? = null
        protected set

    companion object {
        fun createReturnedItem(
            bookId: Long,
            bookTitle: String,
            returnedDate: LocalDate,
        ): ReturnedItem {
            return ReturnedItem(
                bookId,
                bookTitle,
                returnedDate,
            )
        }
    }
}
