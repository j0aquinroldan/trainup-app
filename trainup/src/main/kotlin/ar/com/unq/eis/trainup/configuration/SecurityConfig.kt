package ar.com.unq.eis.trainup.configuration

import ar.com.unq.eis.trainup.jwt.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    val authProvider: AuthenticationProvider,
    val jwtAuthenticationFilter: JwtAuthenticationFilter
) {


    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { auth ->
            auth.requestMatchers("api/auth/**").permitAll()
                .requestMatchers("api/rutinas/admin/**").hasRole("ADMIN")
                .requestMatchers("api/usuario/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}