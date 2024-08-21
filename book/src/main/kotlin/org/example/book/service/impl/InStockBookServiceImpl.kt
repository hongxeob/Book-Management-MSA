package org.example.book.service.impl

import org.example.book.exception.InStockBookException
import org.example.book.repository.InStockBookRepository
import org.example.book.service.InStockBookService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class InStockBookServiceImpl(
    private val inStockBookRepository: InStockBookRepository,
) : InStockBookService {
    @Transactional
    override fun delete(id: Long) {
        val inStockBook =
            inStockBookRepository.findById(id).orElseThrow {
                throw InStockBookException("ID로 재고 도서를 찾을 수 없습니다.")
            }

        inStockBookRepository.delete(inStockBook)
    }
}
