package ar.com.unq.eis.trainup.controller.dto

import ar.com.unq.eis.trainup.model.Role
import ar.com.unq.eis.trainup.model.User
import java.time.LocalDate


class UsuarioDTO(
    var id: String? = null,
    var username: String = "",
    var password: String = "",
    var rutinasSeguidas: List<RutinaDTO> = mutableListOf(),
    var rutinasCompletadas: List<RutinaDTO> = mutableListOf(),
    var nombre: String = "",
    var edad: Int? = null,
    var fecNacimiento: LocalDate? = null,
    var telefono: String = "",
    var genero: String = "",
    var altura: String = "",
    var peso: String = "",
    var objetivo: String = "",
    var rol: Role = Role.USER,
    var rutinasFavoritas: List<RutinaDTO> = mutableListOf()
) {

<<<<<<< Updated upstream
    fun aModelo(): Usuario {
        val usuario = Usuario(username, password, nombre, edad!!, fecNacimiento!!, telefono, genero, altura, peso, objetivo, rol) // Incluir esAdmin
        usuario.id = id
        usuario.rutinasSeguidas.addAll(rutinasSeguidas.map { it.aModelo() })
        usuario.rutinasCompletadas.addAll(rutinasCompletadas.map { it.aModelo() })
        usuario.rutinasFavoritas.addAll(rutinasFavoritas.map { it.aModelo() })
=======
    fun aModelo(): User {
        val user = User(username, password, nombre, edad!!, fecNacimiento!!, telefono, genero, altura, peso, objetivo, rol) // Incluir esAdmin
        user.id = id
        user.followingRoutines.addAll(rutinasSeguidas.map { it.aModelo() })
        user.completedRoutines.addAll(rutinasCompletadas.map { it.aModelo() })
        user.favRoutines.addAll(rutinasFavoritas.map { it.aModelo() })
>>>>>>> Stashed changes

        return user
    }

    companion object {
        fun desdeModelo(user: User): UsuarioDTO {
            val usuarioDto = UsuarioDTO(
<<<<<<< Updated upstream
                usuario.id,
                usuario.username,
                usuario.password,
                usuario.rutinasSeguidas.map { RutinaDTO.desdeModelo(it) },
                usuario.rutinasCompletadas.map { RutinaDTO.desdeModelo(it) },
                usuario.nombre,
                usuario.edad,
                usuario.fecNacimiento,
                usuario.telefono,
                usuario.genero,
                usuario.altura,
                usuario.peso,
                usuario.objetivo,
                usuario.rol // Mapear esAdmin
            )

            usuarioDto.rutinasFavoritas = usuario.rutinasFavoritas.map { RutinaDTO.desdeModelo(it) }
=======
                user.id,
                user.username,
                user.password,
                user.followingRoutines.map { RutinaDTO.desdeModelo(it) },
                user.completedRoutines.map { RutinaDTO.desdeModelo(it) },
                user.name,
                user.age,
                user.birthDate,
                user.phone,
                user.genre,
                user.height,
                user.weight,
                user.objective,
                user.role // Mapear esAdmin
            )

            usuarioDto.rutinasFavoritas = user.favRoutines.map { RutinaDTO.desdeModelo(it) }
>>>>>>> Stashed changes
            return usuarioDto
        }
    }
}


class UserBodyDTO(
    var id: String? = null,
    var username: String = "",
    var password: String = "",
    var nombre: String = "",
    var edad: Int? = null,
    var fecNacimiento: LocalDate? = null,
    var telefono: String = "",
    var genero: String = "",
    var altura: String = "",
    var peso: String = "",
    var objetivo: String = ""
) {

    fun aModelo(): User {
        return User(
            username = this.username,
            password = this.password,
            nombre = this.nombre,
            edad = this.edad!!,
            fecNacimiento = this.fecNacimiento ?: LocalDate.now(),
            telefono = this.telefono,
            genero = this.genero,
            altura = this.altura,
            peso = this.peso,
            objetivo = this.objetivo
        )
    }

    companion object {
        fun desdeModelo(user: User): UserBodyDTO {
            return UserBodyDTO(
<<<<<<< Updated upstream
                id = usuario.id,
                username = usuario.username,
                password = usuario.password,
                nombre = usuario.nombre,
                fecNacimiento = usuario.fecNacimiento,
                telefono = usuario.telefono,
                genero = usuario.genero,
                altura = usuario.altura,
                edad = usuario.edad,
                peso = usuario.peso,
                objetivo = usuario.objetivo
=======
                id = user.id,
                username = user.username,
                password = user.password,
                nombre = user.name,
                fecNacimiento = user.birthDate,
                telefono = user.phone,
                genero = user.genre,
                altura = user.height,
                edad = user.age,
                peso = user.weight,
                objetivo = user.objective
>>>>>>> Stashed changes
            )
        }
    }
}

