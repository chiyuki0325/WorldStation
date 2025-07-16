package ink.chyk.worldstation.configuration

import ink.chyk.worldstation.util.PageRequestMatcher
import org.springframework.context.annotation.*
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.*
import org.springframework.security.config.annotation.web.configuration.*
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher
import org.springframework.security.web.util.matcher.AndRequestMatcher
import org.springframework.security.web.util.matcher.ParameterRequestMatcher
import org.springframework.security.web.util.matcher.RegexRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher



@Configuration
@EnableWebSecurity
class SecurityConfig {
    companion object {
        val DEBUG_DISABLE_WEB_SECURITY = false
    }

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
                val authenticated2 = if (DEBUG_DISABLE_WEB_SECURITY) {
                    // 允许所有请求，方便测试
                    permitAll
                } else {
                    authenticated
                }
                authorize("/", permitAll)
                authorize(AndRequestMatcher(
                    PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/api/worldmaps"),
                    PageRequestMatcher(2)
                ), permitAll)
                authorize(PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/api/versions"), permitAll)
                authorize("/login/**", permitAll)
                authorize("/oauth2/**", permitAll)
                authorize("/docs/**", permitAll)
               authorize(anyRequest, authenticated2) // 需要认证的请求
            }
            if (!DEBUG_DISABLE_WEB_SECURITY) {
                oauth2Login {
                    defaultSuccessUrl("/", true)
                }
            }
        }
        return http.build()
    }
}
