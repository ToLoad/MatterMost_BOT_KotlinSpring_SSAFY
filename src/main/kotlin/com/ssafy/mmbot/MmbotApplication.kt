package com.ssafy.mmbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class MmbotApplication

fun main(args: Array<String>) {
	runApplication<MmbotApplication>(*args)
}
