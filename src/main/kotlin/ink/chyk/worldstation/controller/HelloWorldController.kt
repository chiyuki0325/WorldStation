package ink.chyk.worldstation.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {
    @GetMapping("/")
    fun helloWorld(): String {
        return "Hello, World!"
    }

    @GetMapping("/pageNeedsAuth")
    fun pageNeedsAuth(): String {
        return "This page needs authentication!"
    }
}
