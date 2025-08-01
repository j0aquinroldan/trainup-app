package ar.com.unq.eis.trainup.jwt

import ar.com.unq.eis.trainup.services.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {



    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = this.extractToken(request)

        if (token == null) {
            filterChain.doFilter(request, response)
            return
        }

        val username = jwtService.getUsernameFromToken(token)
        val rol = jwtService.getRoleFromToken(token)


        if (username != null && SecurityContextHolder.getContext().authentication == null) {

            val userDetails = userDetailsService.loadUserByUsername(username)
            val updatedUser :User

            if (jwtService.isTokenValid(token, userDetails)) {

                updatedUser = User(
                    userDetails.username, userDetails.password, listOf(SimpleGrantedAuthority("ROLE_$rol"))
                )

                val userAuth = UsernamePasswordAuthenticationToken(
                    updatedUser, null, updatedUser.authorities
                )
                userAuth.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = userAuth
            }
        }

        return filterChain.doFilter(request, response)
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (StringUtils.hasText(header) && header.startsWith("Bearer")) {
            return header.substring(7)
        }
        return null;
    }


}