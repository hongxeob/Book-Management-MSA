@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.example.rental.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.time.LocalDate

@Entity
@Table(name = "rented_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class RentedItem(
    bookId: Long,
    bookTitle: String,
    rentedDate: LocalDate,
    rental: Rental,
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

    @Column(name = "rented_dated")
    var rentedDated: LocalDate = rentedDate
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnoreProperties("rentedItems")
    var rental: Rental = rental
        protected set
}
