package org.example.book.repository

import org.example.book.domain.InStockBook
import org.springframework.data.jpa.repository.JpaRepository

interface InStockBookRepository : JpaRepository<InStockBook, Long>
