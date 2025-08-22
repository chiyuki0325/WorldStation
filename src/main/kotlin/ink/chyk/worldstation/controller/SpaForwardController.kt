package ink.chyk.worldstation.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class SpaForwardController {
    @RequestMapping(value = ["/{path:[^\\.]*}"])
    fun forward(): String {
        return "forward:/static/index.html"
    }
}

