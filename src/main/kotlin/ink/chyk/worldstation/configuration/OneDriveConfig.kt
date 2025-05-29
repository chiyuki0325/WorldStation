package ink.chyk.worldstation.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "onedrive")
class OneDriveConfig {
    var alist: String? = null
    var alistToken: String? = null
}