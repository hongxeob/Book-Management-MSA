package org.example.bookcatalog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class BookCatalogApplication

fun main(args: Array<String>) {
    runApplication<BookCatalogApplication>(*args)
}
