package ar.com.unq.eis.trainup.controller

import ar.com.unq.eis.trainup.controller.dto.LoginDTO
import ar.com.unq.eis.trainup.controller.dto.UserBodyDTO
import ar.com.unq.eis.trainup.controller.dto.UsuarioDTO
import ar.com.unq.eis.trainup.services.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/usuario")
class UsuarioController(
    @Autowired private val usuarioService: UsuarioService
) {

    @PostMapping
    fun crearUsuario(@RequestBody usuarioDTO: UserBodyDTO): ResponseEntity<*> {
        val usuario = usuarioService.crearUsuario(usuarioDTO.aModelo())
        return ResponseEntity.ok(UsuarioDTO.desdeModelo(usuario))
    }

    @GetMapping("username/{username}")
    fun obtenerUsuarioPorUsername(@PathVariable username: String): ResponseEntity<*> {

        val usuario = usuarioService.obtenerUsuarioPorUsername(username)
        return ResponseEntity.ok(UsuarioDTO.desdeModelo(usuario))

    }

    @GetMapping("id/{id}")
    fun obtenerUsuarioPorID(@PathVariable id: String): ResponseEntity<*> {
        val usuario = usuarioService.obtenerUsuarioPorID(id)
        return ResponseEntity.ok(UsuarioDTO.desdeModelo(usuario))
    }

    @GetMapping("/me")
    fun getCurrentUser(@AuthenticationPrincipal userDetails: UserDetails):ResponseEntity<UsuarioDTO>{

        val user = UsuarioDTO.desdeModelo(this.usuarioService.obtenerUsuarioPorUsername(userDetails.username))

        return ResponseEntity.ok(user)
    }

    @GetMapping
    fun obtenerUsuarios(): ResponseEntity<List<UsuarioDTO>> {
        val usuarios = usuarioService.obtenerUsuarios()
        return ResponseEntity.ok(usuarios.map { usuario -> UsuarioDTO.desdeModelo(usuario) })
    }

    @PutMapping()
    fun actualizarUsuario(@RequestBody usuarioDTO: UsuarioDTO): ResponseEntity<Any> {
        val usuario = usuarioService.actualizarUsuario(usuarioDTO.aModelo())
        return ResponseEntity.ok(UsuarioDTO.desdeModelo(usuario))
    }

    @DeleteMapping("/{id}")
    fun eliminarUsuario(@PathVariable userId: String): ResponseEntity<Any> {
        usuarioService.eliminarUsuario(userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @PostMapping("/login")
    fun loguearUsuario(@RequestBody loginDTO: LoginDTO): ResponseEntity<*> {

        val username = loginDTO.username
        val password = loginDTO.password

        if (username.isBlank() || password.isBlank()) {
            throw IllegalArgumentException("body invalido")
        }
        val usuario = usuarioService.logIn(username, password)
        return ResponseEntity.ok(UsuarioDTO.desdeModelo(usuario))
    }

    @PostMapping("/completarRutina/{userId}/{rutinaId}")
    fun completarRutina(@PathVariable userId: String, @PathVariable rutinaId: String): ResponseEntity<*> {
        usuarioService.completarRutina(userId, rutinaId)
        return ResponseEntity.ok("rutina completada exitosamente")
    }

    @PutMapping("/{userId}/completarONoEjercicio/{rutinaId}/{ejercicioId}")
    fun completarONoEjercicio(
        @PathVariable userId: String,
        @PathVariable rutinaId: String,
        @PathVariable ejercicioId: String
    ): ResponseEntity<Any> {

        usuarioService.completarEjercicio(userId, rutinaId, ejercicioId)
        return ResponseEntity.ok("ejercicio completado exitosamente")
    }

    @PutMapping("/follow/{userId}/{rutinaId}")
    fun updateFollow(@PathVariable userId: String, @PathVariable rutinaId: String): ResponseEntity<*> {
        val user = usuarioService.updateFollowRutina(userId, rutinaId)
        return ResponseEntity.ok(UsuarioDTO.desdeModelo(user))
    }

    @GetMapping("/isFollowing/{userId}/{rutinaId}")
    fun isFollowing(@PathVariable userId: String, @PathVariable rutinaId: String): ResponseEntity<*> {
        val res = usuarioService.isFollowing(userId, rutinaId)
        return ResponseEntity.ok(res)
    }

    @PutMapping("/{idUsuario}/favorita/{idRutina}")
    fun agregarRutinaFavorita(@PathVariable idUsuario: String, @PathVariable idRutina: String): ResponseEntity<Any> {
        val usuario = usuarioService.agregarRutinaFavorita(idUsuario, idRutina)
        return ResponseEntity.ok(UsuarioDTO.desdeModelo(usuario))
    }
}