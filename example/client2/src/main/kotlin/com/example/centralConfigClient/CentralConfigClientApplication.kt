package com.example.centralConfigClient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CentralConfigClientApplication

fun main(args: Array<String>) {
	runApplication<CentralConfigClientApplication>(*args)
}
