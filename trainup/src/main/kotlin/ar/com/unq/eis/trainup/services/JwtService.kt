package ar.com.unq.eis.trainup.services

import ar.com.unq.eis.trainup.model.Usuario
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${jwt.secret}") private val secretKey: String
) {


    fun getToken(user: Usuario): String {
        return getToken(hashMapOf(Pair("rol", user.rol)), user)
    }

    fun getToken(extraClaims: Map<String, Any>, user: UserDetails): String {
        return Jwts.builder()
            .claims(extraClaims)
            .subject(user.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
            .signWith(getKey()).compact()
    }

    fun getKey(): SecretKey {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey))
    }

    fun getUsernameFromToken(token: String): String? {
        return getClaim(token, Claims::getSubject)
    }

    fun getRoleFromToken(token: String): String {
        return getClaim(token) { claims ->
            claims["rol"] as String
        }
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun getAllClaims(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun <T> getClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = getAllClaims(token)
        return claimsResolver(claims)
    }

    fun getExpiration(token: String): Date {
        return getClaim(token, Claims::getExpiration)
    }

    fun isTokenExpired(token: String): Boolean {
        return getExpiration(token).before(Date())
    }
}
