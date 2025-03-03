package ar.com.unq.eis.trainup.services

import ar.com.unq.eis.trainup.controller.dto.AuthResponse
import ar.com.unq.eis.trainup.controller.dto.LoginDTO
import ar.com.unq.eis.trainup.model.Usuario

interface AuthService {
    fun login(loginDTO: LoginDTO): AuthResponse
    fun register(usuario: Usuario): AuthResponse
}
