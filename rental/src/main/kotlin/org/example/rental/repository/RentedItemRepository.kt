package org.example.rental.repository

import org.example.rental.domain.RentedItem
import org.springframework.data.jpa.repository.JpaRepository

interface RentedItemRepository : JpaRepository<RentedItem, Long>
