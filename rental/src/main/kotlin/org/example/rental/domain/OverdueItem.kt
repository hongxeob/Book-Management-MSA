@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.example.rental.domain

import jakarta.persistence.*
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.time.LocalDate

@Entity
@Table(name = "overdue_items")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class OverdueItem(
    bookId: Long,
    bookTitle: String,
    dueDate: LocalDate,
) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(name = "book_id")
    var bookId = bookId
        protected set

    @Column(name = "book_title")
    var bookTitle = bookTitle
        protected set

    @Column(name = "due_date")
    var dueDate = dueDate
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var rental: Rental? = null
        protected set

    companion object {
        fun createOverdueItem(
            bookId: Long,
            bookTitle: String,
            dueDate: LocalDate,
        ): OverdueItem {
            return OverdueItem(
                bookId,
                bookTitle,
                dueDate,
            )
        }
    }
}
