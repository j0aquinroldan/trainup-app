package ar.com.unq.eis.trainup.services

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import java.util.function.Function

@Service
class JwtService {

    private val SECRET_KEY = Jwts.SIG.HS256.key().build()

    fun getToken(user: UserDetails): String {
        return getToken(HashMap(), user)
    }

    fun getToken(extraClaims: Map<String, Any>, user: UserDetails): String {
        return Jwts.builder().claims(extraClaims).subject(user.username).issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)).signWith(getKey()).compact()
    }

    fun getKey(): Key {
        return SECRET_KEY
    }

    fun getUsernameFromToken(token: String): String? {
        return getClaim(token, Claims::getSubject)
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun getAllClaims(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(SECRET_KEY)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun <T> getClaim(token:String, claimsResolver: (Claims)->T ):T{
        val claims = getAllClaims(token)
        return claimsResolver(claims)
    }

    fun getExpiration(token: String):Date{
        return getClaim(token, Claims::getExpiration)
    }

    fun isTokenExpired(token: String):Boolean{
        return getExpiration(token).before(Date())
    }
}
