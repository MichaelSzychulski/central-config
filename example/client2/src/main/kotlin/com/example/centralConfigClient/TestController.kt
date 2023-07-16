package com.example.centralConfigClient

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @Value("\${example.animal}")
    lateinit var animal: String

    @Value("\${example.fruit}")
    lateinit var fruit: String

    @Value("\${example.branch}")
    lateinit var branch: String

    @Value("\${example.profile}")
    lateinit var profile: String

    @GetMapping("/config")
    fun getConfig(): Map<String, String> {
        return mapOf(
            "animal" to animal,
            "fruit" to fruit,
            "branch" to branch,
            "profile" to profile
        )
    }
}