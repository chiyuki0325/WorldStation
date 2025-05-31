package ink.chyk.worldstation.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.*
import org.springframework.web.servlet.config.annotation.*


@Configuration
class AsyncConfig : AsyncConfigurer {
    fun configureAsyncSupport(configurer: AsyncSupportConfigurer) {
        // 设置异步超时为2小时（适合2GB@4MB/s上传）
        configurer.setDefaultTimeout(7200000)
    }
}
