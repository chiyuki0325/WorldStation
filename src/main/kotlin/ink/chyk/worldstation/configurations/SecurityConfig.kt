package ink.chyk.worldstation.configurations

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    val ADMIN: String = "ROLE_ADMIN"
    val USER: String = "ROLE_USER"
    val ANONYMOUS: String = "ROLE_ANONYMOUS"


    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .authorizeHttpRequests(Customizer { auth ->
                auth
                    // 首页无需登录
                    .requestMatchers("/")
                    .permitAll()
                    .anyRequest().hasAuthority(USER)
            }
            )

        return http.build()
    }
}
