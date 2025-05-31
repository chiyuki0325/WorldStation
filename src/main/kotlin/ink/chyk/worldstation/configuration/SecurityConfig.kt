package ink.chyk.worldstation.configuration

import org.springframework.context.annotation.*
import org.springframework.security.config.annotation.web.builders.*
import org.springframework.security.config.annotation.web.configuration.*
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler


private const val DEBUG_DISABLE_WEB_SECURITY = false

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        // fixes from https://stackoverflow.com/questions/74447118/csrf-protection-not-working-with-spring-security-6

        // 修复 Spring Security 6 中，默认不提供 CSRF 令牌的问题
        val requestHandler = CsrfTokenRequestAttributeHandler()
        requestHandler.setCsrfRequestAttributeName(null)

        http {
            csrf {
                csrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse()
                csrfTokenRequestHandler = requestHandler
            }
            authorizeHttpRequests {
                authorize("/", permitAll)
                authorize("/login/**", permitAll)
                authorize("/oauth2/**", permitAll)
                authorize("/docs/**", permitAll)
                if (DEBUG_DISABLE_WEB_SECURITY)
                    authorize(anyRequest, permitAll) // 允许所有请求，方便测试
                else
                    authorize(anyRequest, authenticated) // 需要认证的请求
            }
            if (!DEBUG_DISABLE_WEB_SECURITY) {
                oauth2Login {
                    defaultSuccessUrl("/api/user", true)
                }
            }
        }
        return http.build()
    }
}
