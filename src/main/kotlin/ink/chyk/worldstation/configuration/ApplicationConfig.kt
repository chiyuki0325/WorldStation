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
        val requestFactory = SimpleClientHttpRequestFactory()


        // 配置代理
        val proxy: Proxy = Proxy(
            Proxy.Type.HTTP,
            InetSocketAddress("127.0.0.1", 8888)
        )
        requestFactory.setProxy(proxy)


        return RestTemplate(requestFactory)
    }

}
