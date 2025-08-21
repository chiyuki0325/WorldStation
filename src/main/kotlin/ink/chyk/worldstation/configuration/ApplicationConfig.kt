package ink.chyk.worldstation.configuration

import org.springframework.context.annotation.*
import org.springframework.http.client.*
import org.springframework.web.client.*
import java.net.InetSocketAddress
import java.net.Proxy


@Configuration
class ApplicationConfig {

    @Bean
    fun restTemplate(): RestTemplate {
        val requestFactory = SimpleClientHttpRequestFactory().apply {
            setConnectTimeout(1800_000)  // 30分钟
            setReadTimeout(1800_000)     // 30分钟
        }

        // 配置代理

        val proxy = Proxy(
            Proxy.Type.HTTP,
            InetSocketAddress("127.0.0.1", 8888)
        )
        requestFactory.setProxy(proxy)



        return RestTemplate(requestFactory)
    }

}
