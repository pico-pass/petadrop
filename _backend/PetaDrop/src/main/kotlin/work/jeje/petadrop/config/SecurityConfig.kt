package work.jeje.petadrop.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain
import work.jeje.petadrop.config.auth.CustomOAuth2UserService
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import work.jeje.petadrop.domain.user.Role


@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Autowired
    private val customOAuth2UserService: CustomOAuth2UserService? = null
    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring()
                .requestMatchers("/error")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        }
    }
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            authorizeRequests {
                authorize("/", hasRole(Role.USER.name))
                authorize("/favicon.ico", permitAll)
                authorize("/oauth/**", permitAll)
                authorize("/api/v1/**", hasRole(Role.USER.name))
            }
            logout {
                logoutSuccessUrl = "/"
            }
            oauth2Login {
                userInfoEndpoint { userService = customOAuth2UserService }
                defaultSuccessUrl("/user", true)
            }
            formLogin { }
        }
        return http.build()
    }
}