package ar.com.unq.eis.trainup.controller

import ar.com.unq.eis.trainup.controller.dto.UsuarioDTO
import ar.com.unq.eis.trainup.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/usuario")
class UserController(
    @Autowired private val userService: UserService
) {

    @GetMapping("username/{username}")
    fun getUserByUsername(@PathVariable username: String): ResponseEntity<*> {

        val user = userService.getUserByUsername(username)
        return ResponseEntity.ok(UsuarioDTO.desdeModelo(user))

    }

    @GetMapping("id/{id}")
    fun getUserByID(@PathVariable id: String): ResponseEntity<*> {
        val user = userService.getUserByID(id)
        return ResponseEntity.ok(UsuarioDTO.desdeModelo(user))
    }

    @GetMapping("/me")
    fun getCurrentUser(@AuthenticationPrincipal userDetails: UserDetails):ResponseEntity<UsuarioDTO>{

        val user = UsuarioDTO.desdeModelo(this.userService.getUserByUsername(userDetails.username))

        return ResponseEntity.ok(user)
    }

    @GetMapping
    fun getUsers(): ResponseEntity<List<UsuarioDTO>> {
        val users = userService.getUsers()
        return ResponseEntity.ok(users.map { user -> UsuarioDTO.desdeModelo(user) })
    }

    @PutMapping()
    fun updateUser(@RequestBody userDTO: UsuarioDTO): ResponseEntity<Any> {
        val user = userService.updateUser(userDTO.aModelo())
        return ResponseEntity.ok(UsuarioDTO.desdeModelo(user))
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String): ResponseEntity<Any> {
        userService.deleteUser(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @PostMapping("/completarRutina/{userId}/{routineID}")
    fun completeRoutine(@PathVariable userId: String, @PathVariable routineID: String): ResponseEntity<*> {
        userService.completeRoutine(userId, routineID)
        return ResponseEntity.ok("rutina completada exitosamente")
    }

    @PutMapping("/{userId}/completarONoEjercicio/{routineID}/{exerciseID}")
    fun toggleExerciseCompletion(
        @PathVariable userId: String,
        @PathVariable routineID: String,
        @PathVariable exerciseID: String
    ): ResponseEntity<Any> {

        userService.toggleExerciseCompletion(userId, routineID, exerciseID)
        return ResponseEntity.ok("ejercicio completado exitosamente")
    }

    @PutMapping("/follow/{userId}/{rutinaId}")
    fun updateFollow(@PathVariable userId: String, @PathVariable rutinaId: String): ResponseEntity<*> {
        val user = userService.toggleRoutineFollow(userId, rutinaId)
        return ResponseEntity.ok(UsuarioDTO.desdeModelo(user))
    }

    @GetMapping("/isFollowing/{userId}/{rutinaId}")
    fun isFollowing(@PathVariable userId: String, @PathVariable rutinaId: String): ResponseEntity<*> {
        val res = userService.isFollowing(userId, rutinaId)
        return ResponseEntity.ok(res)
    }

    @PutMapping("/{userID}/favorita/{routineID}")
    fun addFavRoutine(@PathVariable userID: String, @PathVariable routineID: String): ResponseEntity<Any> {
        val user = userService.addFavRoutine(userID, routineID)
        return ResponseEntity.ok(UsuarioDTO.desdeModelo(user))
    }
}