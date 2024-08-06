@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.example.rental.domain

import jakarta.persistence.*
import org.example.rental.exception.RentalException
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.time.LocalDate

@Entity
@Table(name = "rental")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class Rental(
    userId: Long,
    rentalStatus: RentalStatus,
    lateFee: Long,
) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(name = "user_id")
    var userId = userId

    @Enumerated(EnumType.STRING)
    @Column(name = "rental_status")
    var rentalStatus = rentalStatus

    @Column(name = "late_fee")
    var lateFee = lateFee

    // 대출아이템
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "rental")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    protected val mutableRentedItems: MutableSet<RentedItem> = mutableSetOf()
    val rentedItems: Set<RentedItem> get() = mutableRentedItems.toSet()

    // 연체아이템
    @OneToMany(mappedBy = "rental", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    protected val mutableOverdueItems: MutableSet<OverdueItem> = mutableSetOf()
    val overdueItems: Set<OverdueItem> get() = mutableOverdueItems.toSet()

    // 반납아이템
    @OneToMany(mappedBy = "rental", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    protected val mutableReturnedItems: MutableSet<ReturnedItem> = mutableSetOf()
    val returnedItems: Set<ReturnedItem> get() = mutableReturnedItems.toSet()

    fun checkRentalAvailable(): Boolean {
        if (this.rentalStatus == RentalStatus.RENT_UNAVAILABLE || this.lateFee != 0L) {
            throw RentalException("Rent is unavailable")
        }

        if (this.rentedItems.size >= 5) {
            throw RentalException("대출 도서 가능한 도서의 수는 ${5 - this.rentedItems.size} 권 입니다.")
        }

        return true
    }

    // 대출 처리 메서드
    fun rentBook(
        bookId: Long,
        title: String,
    ): Rental {
        this.addRentedItem(RentedItem.createRentedItem(bookId, title, LocalDate.now()))
        return this
    }

    // 반납 처리 메서드
    fun returnBook(bookId: Long): Rental {
        val rentedItem = this.rentedItems.first { it -> it.bookId == bookId }
        this.addReturnedItem(ReturnedItem.createReturnedItem(rentedItem.bookId, rentedItem.bookTitle, LocalDate.now()))
        this.removeRentedItem(rentedItem)

        return this
    }

    fun overdueBook(bookId: Long): Rental {
        val rentedItem = this.rentedItems.first { it -> it.bookId == bookId }
        this.addOverdueItem(
            OverdueItem.createOverdueItem(
                rentedItem.bookId,
                rentedItem.bookTitle,
                rentedItem.dueDate,
            ),
        )
        this.removeRentedItem(rentedItem)

        return this
    }

    fun addRentedItem(createRentedItem: RentedItem) {
        mutableRentedItems.add(createRentedItem)
    }

    fun removeRentedItem(rentedItem: RentedItem) {
        mutableRentedItems.remove(rentedItem)
    }

    fun addReturnedItem(returnedItem: ReturnedItem) {
        mutableReturnedItems.add(returnedItem)
    }

    fun addOverdueItem(overdueItem: OverdueItem) {
        mutableOverdueItems.add(overdueItem)
    }

    companion object {
        fun createRental(userId: Long): Rental {
            return Rental(
                userId,
                RentalStatus.RENT_AVAILABLE,
                0,
            )
        }
    }
}
