@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.example.book.domain

import jakarta.persistence.*

@Entity
@Table(name = "book")
class Book(
    title: String,
    author: String,
    description: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "title")
    var title = title

    @Column(name = "author")
    var author = author

    @Column(name = "description")
    var description = description
}
