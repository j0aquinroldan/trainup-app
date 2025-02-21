package ar.com.unq.eis.trainup.services.impl

import ar.com.unq.eis.trainup.dao.RutinaDAO
import ar.com.unq.eis.trainup.model.Ejercicio
import ar.com.unq.eis.trainup.model.Rutina
import ar.com.unq.eis.trainup.services.RutinaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrElse

@Service
@Transactional
class RutinaServiceImpl : RutinaService {

    @Autowired
    lateinit var rutinaDAO: RutinaDAO

    override fun crearRutina(rutina: Rutina): Rutina {
        return rutinaDAO.save(rutina)
    }

    override fun obtenerRutinas(): List<Rutina> {
        return rutinaDAO.findAll()
    }

    override fun obtenerRutinasPag(pageRequest: PageRequest): Map<String, Any> {
        val page = rutinaDAO.findAll(pageRequest)
        val resp = mapOf(
            "content" to page.content,
            "currentPage" to page.number,
            "totalElements" to page.totalElements
        )
        return resp
    }

    override fun obtenerRutinaPorId(id: String): Rutina {
        return rutinaDAO.findById(id).orElseThrow {
            NoSuchElementException("No se encontró la rutina con id: $id")
        }
    }

    override fun actualizarRutina(rutinaActualizada: Rutina): Rutina {

        val id = rutinaActualizada.id ?: throw IllegalArgumentException("id no puede ser null")
        val rutinaExistente = rutinaDAO.findById(id)
            .getOrElse { throw NoSuchElementException("No se encontró la rutina con id: $id") }

        rutinaExistente.nombre = rutinaActualizada.nombre
        rutinaExistente.descripcion = rutinaActualizada.descripcion
        rutinaExistente.categoria = rutinaActualizada.categoria
        rutinaExistente.dificultad = rutinaActualizada.dificultad

        return rutinaDAO.save(rutinaExistente);
    }

    override fun eliminarRutina(id: String) {
        if (rutinaDAO.existsById(id)) {
            rutinaDAO.deleteById(id)
        } else {
            throw NoSuchElementException("No se encontró la rutina con id: $id")
        }
    }

    override fun agregarEjercicio(id: String, ejercicio: Ejercicio): Rutina {
        val rutina = obtenerRutinaPorId(id)
        rutina.agregarEjercicio(ejercicio)
        return rutinaDAO.save(rutina)
    }

    override fun eliminarEjercicio(idRutina: String, idEj: String): Rutina {
        val rutina = obtenerRutinaPorId(idRutina)
        rutina.eliminarEjercicio(idEj)
        return rutinaDAO.save(rutina)
    }

    override fun obtenerRutinasPorCategoria(categoria: String): List<Rutina> {
        return this.rutinaDAO.findByCategoria(categoria)
    }

    override fun buscarRutinas(nombre: String, dificultad: String?): List<Rutina> {
        return if (!dificultad.isNullOrBlank()) {
            this.rutinaDAO.findByNombreContainingIgnoreCaseAndDificultad(nombre, dificultad)
        } else {
            this.rutinaDAO.findByNombreContainingIgnoreCase(nombre)
        }
    }
}