package ar.com.unq.eis.trainup.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document(collection = "rutinas")
class Rutina {

    @Id
    var id: String? = null
    var nombre: String = ""
    var img: String?=null
    var descripcion: String = ""
    var categoria: String = ""
    var dificultad: String = ""
    var duracionMinutos: Int = 0
    var objetivo: String = ""
    var frecuenciaSemanal: Int = 0
    var ejercicios: MutableList<Ejercicio> = mutableListOf()
    var fechaCreacion: LocalDateTime = LocalDateTime.now()

    constructor()

    constructor(
        id: String?,
        nombre: String,
        img: String?,
        descripcion: String,
        categoria: String,
        dificultad: String,
        duracionMinutos: Int,
        objetivo: String,
        frecuenciaSemanal: Int,
        ejercicios: MutableList<Ejercicio> = mutableListOf()
    ) {
        // Validaciones
        require(nombre.isNotBlank()) { "El nombre no puede estar vacío" }
        require(descripcion.isNotBlank()) { "La descripción no puede estar vacía" }
        require(categoria.isNotBlank()) { "La categoría no puede estar vacía" }
        require(dificultad.isNotBlank()) { "La dificultad no puede estar vacía" }
        require(duracionMinutos > 0) { "La duración debe ser mayor a 0 minutos" }
        require(frecuenciaSemanal > 0) { "La frecuencia semanal debe ser mayor a 0" }

        this.id = id
        this.nombre = nombre
        this.img = img
        this.descripcion = descripcion
        this.categoria = categoria
        this.dificultad = dificultad
        this.duracionMinutos = duracionMinutos
        this.objetivo = objetivo
        this.frecuenciaSemanal = frecuenciaSemanal
        this.ejercicios = ejercicios
        this.fechaCreacion = LocalDateTime.now()
    }

    constructor(
        nombre: String,
        img: String?,
        descripcion: String,
        categoria: String,
        ejercicios: MutableList<Ejercicio> = mutableListOf(),
        dificultad: String
    ) {
        // Validaciones
        require(nombre.isNotEmpty()) { "El nombre de la rutina no puede estar vacía" }
        require(descripcion.isNotEmpty()) { "La descripción de la rutina no puede estar vacía" }
        require(categoria.isNotEmpty()) { "La categoría no puede estar vacía" }
        require(dificultad.isNotEmpty()) { "La dificultad no puede estar vacía" }

        this.nombre = nombre
        this.img = img
        this.descripcion = descripcion
        this.dificultad = dificultad
        this.categoria = categoria
        this.ejercicios = ejercicios
        this.fechaCreacion = LocalDateTime.now()
    }

    fun agregarEjercicio(ejercicio: Ejercicio) {

        //actualizar el ejercicio si existe, sino agregarlo
        val index = ejercicios.indexOfFirst { it.id == ejercicio.id }
        if (index != -1) {
            ejercicios[index] = ejercicio
        } else {
            ejercicios.add(ejercicio)
        }
    }

    fun eliminarEjercicio(idEjercicio: String) {
        if(!ejercicios.removeIf{it.id == idEjercicio}){
            throw NoSuchElementException("No se encontró el ejercicio con id: $idEjercicio")
        }
    }

    fun listarEjercicios(): List<Ejercicio> {
        return ejercicios
    }

    override fun toString(): String {
        return "Rutina(id='$id', nombre='$nombre', descripcion='$descripcion', categoria='$categoria', dificultad='$dificultad', duracionMinutos=$duracionMinutos, objetivo='$objetivo', frecuenciaSemanal=$frecuenciaSemanal, ejercicios=$ejercicios, fechaCreacion=$fechaCreacion)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val rutina = other as Rutina?
        return id == rutina!!.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)}

}
