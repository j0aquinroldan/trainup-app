package ar.com.unq.eis.trainup.services.impl

import ar.com.unq.eis.trainup.dao.RoutineDAO
import ar.com.unq.eis.trainup.model.Exercise
import ar.com.unq.eis.trainup.model.Routine
import ar.com.unq.eis.trainup.services.RoutineService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrElse

@Service
@Transactional
class RoutineServiceImpl : RoutineService {

    @Autowired
    lateinit var routineDAO: RoutineDAO

    override fun createRoutine(routine: Routine): Routine {
        return routineDAO.save(routine)
    }

    override fun getRoutines(): List<Routine> {
        return routineDAO.findAll()
    }

    override fun getPaginatedRoutines(pageRequest: PageRequest): Map<String, Any> {
        val page = routineDAO.findAll(pageRequest)
        val resp = mapOf(
            "content" to page.content,
            "currentPage" to page.number,
            "totalElements" to page.totalElements
        )
        return resp
    }

    override fun getRoutinesById(id: String): Routine {
        return routineDAO.findById(id).orElseThrow {
            NoSuchElementException("No se encontró la rutina con id: $id")
        }
    }

    override fun updateRoutine(updatedRoutine: Routine): Routine {

        val id = updatedRoutine.id ?: throw IllegalArgumentException("id no puede ser null")
        val rutinaExistente = routineDAO.findById(id)
            .getOrElse { throw NoSuchElementException("No se encontró la rutina con id: $id") }

        rutinaExistente.name = updatedRoutine.name
        rutinaExistente.description = updatedRoutine.description
        rutinaExistente.category = updatedRoutine.category
        rutinaExistente.difficulty = updatedRoutine.difficulty

        return routineDAO.save(rutinaExistente);
    }

    override fun deleteRoutine(id: String) {
        if (routineDAO.existsById(id)) {
            routineDAO.deleteById(id)
        } else {
            throw NoSuchElementException("No se encontró la rutina con id: $id")
        }
    }

    override fun addExercise(id: String, exercise: Exercise): Routine {
        val rutina = getRoutinesById(id)
        rutina.addExercise(exercise)
        return routineDAO.save(rutina)
    }

    override fun deleteExercise(routineID: String, exerciseID: String): Routine {
        val rutina = getRoutinesById(routineID)
        rutina.deleteExercise(exerciseID)
        return routineDAO.save(rutina)
    }

    override fun getRoutinesByCategory(category: String): List<Routine> {
        return this.routineDAO.findByCategory(category)
    }

    override fun searchRoutines(name: String, difficulty: String?): List<Routine> {
        return if (!difficulty.isNullOrBlank()) {
            this.routineDAO.findByNameContainingIgnoreCaseAndDifficulty(name, difficulty)
        } else {
            this.routineDAO.findByNameContainingIgnoreCase(name)
        }
    }
}