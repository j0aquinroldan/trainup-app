package ar.com.unq.eis.trainup.services.impl

import ar.com.unq.eis.trainup.controller.Exceptions.RutinaNoSeguidaException
import ar.com.unq.eis.trainup.controller.Exceptions.UsuarioDuplicadoException
import ar.com.unq.eis.trainup.dao.RutinaDAO
import ar.com.unq.eis.trainup.dao.UsuarioDAO
import ar.com.unq.eis.trainup.model.Rutina
import ar.com.unq.eis.trainup.model.Usuario
import ar.com.unq.eis.trainup.services.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service

@Service
class UsuarioServiceImpl(
    @Autowired private val usuarioDAO: UsuarioDAO,
    @Autowired private val rutinaDAO: RutinaDAO
) : UsuarioService {

    override fun crearUsuario(usuario: Usuario): Usuario {
        return try {
            usuarioDAO.save(usuario)
        } catch (e: DuplicateKeyException) {
            throw UsuarioDuplicadoException(usuario.username)
        }
    }

    override fun obtenerUsuarios(): List<Usuario> {
        return usuarioDAO.findAll()
    }

    override fun obtenerUsuarioPorUsername(username: String): Usuario {
        return usuarioDAO.findByUsername(username).orElseThrow {
            NoSuchElementException("No existe usuario ${username}")
        }
    }

    override fun obtenerUsuarioPorID(id: String): Usuario {
        return usuarioDAO.findById(id).orElseThrow {
            NoSuchElementException("No existe usuario con id ${id}")
        }
    }

    override fun actualizarUsuario(usuarioActualizado: Usuario): Usuario {

        val id = usuarioActualizado.id ?: throw IllegalArgumentException("id no puede ser null")
        val usuarioExistente =
            usuarioDAO.findById(id).orElseThrow { NoSuchElementException("No se encontró el usuario con id: $id") }

        usuarioExistente.nombre = usuarioActualizado.nombre
        usuarioExistente.edad = usuarioActualizado.edad
        usuarioExistente.fecNacimiento = usuarioActualizado.fecNacimiento
        usuarioExistente.telefono = usuarioActualizado.telefono
        usuarioExistente.genero = usuarioActualizado.genero
        usuarioExistente.altura = usuarioActualizado.altura
        usuarioExistente.peso = usuarioActualizado.peso
        usuarioExistente.objetivo = usuarioActualizado.objetivo
        usuarioExistente.username = usuarioActualizado.username

        return usuarioDAO.save(usuarioExistente)
    }

    override fun eliminarUsuario(id: String) {
        if (usuarioDAO.existsById(id)) {
            usuarioDAO.deleteById(id)
        } else {
            throw NoSuchElementException("No existe usuario con id ${id}")
        }
    }

    override fun logIn(username: String, password: String): Usuario {
        return usuarioDAO.findByUsernameAndPassword(username, password)
            .orElseThrow { NoSuchElementException("usuario no encontrado") }
    }

    override fun completarRutina(usuarioID: String, rutinaID: String) {
        val usuario = this.obtenerUsuarioPorID(usuarioID)

        if (!usuario.completarRutina(rutinaID)) {
            throw RutinaNoSeguidaException(usuario.username, rutinaID)
        }
        usuarioDAO.save(usuario)
    }

    private fun getRutinaByID(rutinaID: String): Rutina {
        val rutina =
            this.rutinaDAO.findById(rutinaID).orElseThrow {
                NoSuchElementException("No existe rutina con id ${rutinaID}")
            }
        return rutina
    }

    override fun updateFollowRutina(usuarioID: String, rutinaID: String): Usuario {
        val usuario = this.obtenerUsuarioPorID(usuarioID)
        val rutina = this.getRutinaByID(rutinaID)

        usuario.followUnfollowRutina(rutina)

        return usuarioDAO.save(usuario)
    }

    override fun isFollowing(usuarioID: String, rutinaID: String): Boolean {
        val usuario = this.obtenerUsuarioPorID(usuarioID)

        return usuario.isFollowing(rutinaID)

    }

    override fun completarEjercicio(userId: String, rutinaId: String, ejercicioId: String) {
        val usuario = this.obtenerUsuarioPorID(userId)
        val rutina = this.getRutinaByID(rutinaId)

        if(!usuario.isFollowing(rutinaId)) throw RutinaNoSeguidaException(rutinaId, usuario.username)

        rutina.ejercicios.find { it.id == ejercicioId }
            ?: throw NoSuchElementException("No existe ejercicio con id ${ejercicioId} en la rutina ${rutinaId}")

        usuario.completarEjercicio(rutinaId, ejercicioId)

        usuarioDAO.save(usuario)
    }

    override fun agregarRutinaFavorita(idUsuario: String, idRutina: String): Usuario {
        val usuario = this.obtenerUsuarioPorID(idUsuario)
        val rutina = this.getRutinaByID(idRutina)

        usuario.agregarONoRutinaFavorita(rutina)

        return usuarioDAO.save(usuario)
    }
}