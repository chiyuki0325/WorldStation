package ink.chyk.worldstation.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.*


@RestController
class UserController {
    @GetMapping("/user")
    fun user(@AuthenticationPrincipal principal: OAuth2User): String {
        return "Hello, ${principal.getAttribute<Any?>("nickname")} ${principal.getAttribute<Any?>("id")}!"
    }
}
