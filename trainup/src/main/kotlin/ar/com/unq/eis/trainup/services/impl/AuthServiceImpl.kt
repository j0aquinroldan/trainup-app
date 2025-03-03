package ar.com.unq.eis.trainup.services.impl

import ar.com.unq.eis.trainup.controller.Exceptions.UsuarioDuplicadoException
import ar.com.unq.eis.trainup.controller.dto.AuthResponse
import ar.com.unq.eis.trainup.controller.dto.LoginDTO
import ar.com.unq.eis.trainup.dao.UsuarioDAO
import ar.com.unq.eis.trainup.model.Usuario
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
    private val usuarioDAO: UsuarioDAO,
    @Autowired
    private val jwtService: JwtService,
    @Autowired
    private val passwordEncoder: PasswordEncoder,
    @Autowired
    private val authenticationManager: AuthenticationManager
) : AuthService {


    override fun login(loginDTO: LoginDTO): AuthResponse {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(loginDTO.username, loginDTO.password))
        val user = usuarioDAO.findByUsername(loginDTO.username).orElseThrow {
            NoSuchElementException("No existe usuario ${loginDTO.username}")
        }
        val token = jwtService.getToken(user)

        return AuthResponse(token)
    }

    override fun register(usuario: Usuario): AuthResponse {
        try {
            usuario.password = passwordEncoder.encode(usuario.password)
            usuarioDAO.save(usuario)
        } catch (e: DuplicateKeyException) {
            throw UsuarioDuplicadoException(usuario.username)
        }

        return AuthResponse(token = jwtService.getToken(usuario))
    }
}