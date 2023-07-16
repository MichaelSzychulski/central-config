package com.example.centralConfigServer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class CentralConfigServerApplication

fun main(args: Array<String>) {
	runApplication<CentralConfigServerApplication>(*args)
}
