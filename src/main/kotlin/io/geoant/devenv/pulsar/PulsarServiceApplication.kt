package io.geoant.devenv.pulsar

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PulsarServiceApplication

fun main(args: Array<String>) {
    runApplication<PulsarServiceApplication>(*args)
}