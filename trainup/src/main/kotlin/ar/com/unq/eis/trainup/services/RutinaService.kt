package ar.com.unq.eis.trainup.services

import ar.com.unq.eis.trainup.model.Ejercicio
import ar.com.unq.eis.trainup.model.Rutina
import org.springframework.data.domain.PageRequest


interface RutinaService {

    fun crearRutina(rutina: Rutina): Rutina

    fun obtenerRutinas(): List<Rutina>

    fun obtenerRutinasPag(pageRequest: PageRequest): Map<String, Any>

    fun obtenerRutinaPorId(id: String): Rutina

    fun actualizarRutina(rutinaActualizada: Rutina): Rutina

    fun eliminarRutina(id: String)

    fun agregarEjercicio(id: String, ejercicio: Ejercicio): Rutina

    fun eliminarEjercicio(idRutina: String, idEj: String): Rutina
    fun obtenerRutinasPorCategoria(categoria: String): List<Rutina>
    fun buscarRutinas(nombre: String, dificultad: String?): List<Rutina>

}
