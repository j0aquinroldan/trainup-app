package ar.com.unq.eis.trainup.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

@Document(collection = "usuarios")
class Usuario() {

    @Id
    var id: String? = null

    @Indexed(unique = true)
    var username: String = ""
    var password: String = ""
    var rutinasSeguidas: MutableList<Rutina> = mutableListOf()
    var rutinasCompletadas: MutableList<Rutina> = mutableListOf()
    var nombre: String = ""
    var edad: Int? = null
    var fecNacimiento: LocalDate? = null
    var telefono: String = ""
    var genero: String = ""
    var altura: String = ""
    var peso: String = ""
    var objetivo: String = ""
    var esAdmin: Boolean = false
    var rutinasFavoritas: MutableList<Rutina> = mutableListOf()

    constructor(
        username: String,
        password: String,
        nombre: String,
        edad: Int,
        fecNacimiento: LocalDate,
        telefono: String,
        genero: String,
        altura: String,
        peso: String,
        objetivo: String,
        esAdmin: Boolean = false
    ) : this() {
        require(username.isNotBlank()) { "El username no puede estar vacío" }
        require(password.isNotEmpty()) { "La contraseña no puede estar vacía" }
        require(nombre.isNotEmpty()) { "El nombre del usuario no puede estar vacío" }

        this.username = username
        this.password = password
        this.nombre = nombre
        this.edad = edad
        this.fecNacimiento = fecNacimiento
        this.telefono = telefono
        this.genero = genero
        this.altura = altura
        this.peso = peso
        this.objetivo = objetivo
        this.esAdmin = esAdmin
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val usuario = other as Usuario?
        return id == usuario!!.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    fun completarRutina(idRutina: String):Boolean {

        val rutina = rutinasSeguidas.find { it.id == idRutina } // debe seguir a la rutina para poder completarla

        if (rutina == null) return false

        if (!rutinasCompletadas.contains(rutina)) { // en caso de que no la haya completado previamente se agrega a su historial
            rutinasCompletadas.add(rutina)
        }

        return true
    }

    fun followUnfollowRutina(rutina: Rutina) {
        if (!rutinasSeguidas.removeIf { it.id == rutina.id }) {
            rutinasSeguidas.add(rutina)
        }
    }

    fun isFollowing(rutinaID: String): Boolean {
        return rutinasSeguidas.any { it.id == rutinaID }
    }

    fun completarEjercicio(idRutina: String, idEjercicio: String) {
        rutinasSeguidas.map { rutina ->
            if (rutina.id == idRutina) {
                rutina.ejercicios.map { ejercicio ->
                    if (ejercicio.id == idEjercicio) {
                        ejercicio.completado = true
                    }
                }
            }
        }
    }

    fun agregarONoRutinaFavorita(rutina: Rutina) {
        if (!rutinasFavoritas.removeIf { it.id == rutina.id }) {
            rutinasFavoritas.add(rutina)
        }
    }
}
