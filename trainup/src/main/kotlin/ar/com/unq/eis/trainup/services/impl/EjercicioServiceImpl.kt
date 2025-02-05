package ar.com.unq.eis.trainup.services.impl

import EjercicioService
import ar.com.unq.eis.trainup.dao.EjercicioDAO
import ar.com.unq.eis.trainup.model.Ejercicio
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class EjercicioServiceImpl(
    @Autowired private val ejercicioDAO: EjercicioDAO
) : EjercicioService {

    override fun crearEjercicio(ejercicio: Ejercicio): Ejercicio {
        return ejercicioDAO.save(ejercicio)
    }

    override fun obtenerEjercicios(): List<Ejercicio> {
        return ejercicioDAO.findAll()
    }

    override fun obtenerEjercicioPorId(id: String): Ejercicio {
        return ejercicioDAO.findById(id).orElseThrow {
            NoSuchElementException("No se encontró el ejercicio con id: $id")
        }
    }


    override fun actualizarEjercicio(ejercicioActualizado: Ejercicio): Ejercicio {

        val id = ejercicioActualizado.id ?: throw IllegalArgumentException("id no puede ser null")

        val ejercicioExistente = ejercicioDAO.findById(id).getOrElse {
            throw NoSuchElementException("No se encontró el ejercicio con id: $id")
        }

        ejercicioExistente.peso = ejercicioActualizado.peso
        ejercicioExistente.nombre = ejercicioActualizado.nombre
        ejercicioExistente.descansoSegundos = ejercicioActualizado.descansoSegundos
        ejercicioExistente.series = ejercicioActualizado.series
        ejercicioExistente.repeticiones = ejercicioActualizado.repeticiones
        ejercicioExistente.descripcion = ejercicioActualizado.descripcion
        ejercicioExistente.equipo = ejercicioActualizado.equipo
        ejercicioExistente.instrucciones = ejercicioActualizado.instrucciones
        ejercicioExistente.musculo = ejercicioActualizado.musculo
        ejercicioExistente.completado = ejercicioActualizado.completado

        return ejercicioDAO.save(ejercicioExistente)

    }


    override fun eliminarEjercicio(id: String) {

        if (ejercicioDAO.existsById(id)) {
            ejercicioDAO.deleteById(id)
        } else {
            throw NoSuchElementException("No se encontró el ejercicio con id: $id")
        }
    }
}