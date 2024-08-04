package org.example.book

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class BookApplication

fun main(args: Array<String>) {
    runApplication<BookApplication>(*args)
}
