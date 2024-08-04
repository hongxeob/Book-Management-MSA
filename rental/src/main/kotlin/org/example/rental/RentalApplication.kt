package org.example.rental

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class RentalApplication

fun main(args: Array<String>) {
    runApplication<RentalApplication>(*args)
}
