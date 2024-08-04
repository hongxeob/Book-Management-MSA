@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.example.rental.domain

import jakarta.persistence.*
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable

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
}
