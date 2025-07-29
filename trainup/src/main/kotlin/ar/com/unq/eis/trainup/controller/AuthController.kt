package ar.com.unq.eis.trainup.controller

import ar.com.unq.eis.trainup.controller.dto.AuthResponse
import ar.com.unq.eis.trainup.controller.dto.LoginDTO
import ar.com.unq.eis.trainup.controller.dto.UsuarioDTO
import ar.com.unq.eis.trainup.services.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    @Autowired
    private val authService: AuthService
) {



    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authService.login(loginDTO))
    }

    @PostMapping("/register")
    fun register(@RequestBody usuarioDTO: UsuarioDTO): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authService.register(usuarioDTO.aModelo()))
    }


}