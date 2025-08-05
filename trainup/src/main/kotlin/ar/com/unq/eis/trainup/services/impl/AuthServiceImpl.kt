package ar.com.unq.eis.trainup.services.impl

import ar.com.unq.eis.trainup.controller.Exceptions.UsuarioDuplicadoException
import ar.com.unq.eis.trainup.controller.dto.AuthResponse
import ar.com.unq.eis.trainup.controller.dto.LoginDTO
import ar.com.unq.eis.trainup.dao.UserDAO
import ar.com.unq.eis.trainup.model.User
import ar.com.unq.eis.trainup.services.AuthService
import ar.com.unq.eis.trainup.services.JwtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    @Autowired
    private val userDAO: UserDAO,
    @Autowired
    private val jwtService: JwtService,
    @Autowired
    private val passwordEncoder: PasswordEncoder,
    @Autowired
    private val authenticationManager: AuthenticationManager
) : AuthService {


    override fun login(loginDTO: LoginDTO): AuthResponse {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(loginDTO.username, loginDTO.password))
        val user = userDAO.findByUsername(loginDTO.username).orElseThrow {
            NoSuchElementException("No existe usuario ${loginDTO.username}")
        }
        val token = jwtService.getToken(user)

        return AuthResponse(token)
    }

    override fun register(user: User): AuthResponse {
        try {
            user.password = passwordEncoder.encode(user.password)
            userDAO.save(user)
        } catch (e: DuplicateKeyException) {
            throw UsuarioDuplicadoException(user.username)
        }

        return AuthResponse(token = jwtService.getToken(user))
    }
}