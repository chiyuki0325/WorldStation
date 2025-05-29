package ink.chyk.worldstation.controller

import ink.chyk.worldstation.configuration.OneDriveConfig
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/onedrive")
class OneDriveController(
    private val config: OneDriveConfig
) {
}