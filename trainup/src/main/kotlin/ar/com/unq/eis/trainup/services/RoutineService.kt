package ar.com.unq.eis.trainup.services

import ar.com.unq.eis.trainup.model.Exercise
import ar.com.unq.eis.trainup.model.Routine
import org.springframework.data.domain.PageRequest


interface RoutineService {

    fun createRoutine(routine: Routine): Routine

    fun getRoutines(): List<Routine>

    fun getPaginatedRoutines(pageRequest: PageRequest): Map<String, Any>

    fun getRoutinesById(id: String): Routine

    fun updateRoutine(updatedRoutine: Routine): Routine

    fun deleteRoutine(id: String)

    fun addExercise(id: String, exercise: Exercise): Routine

    fun deleteExercise(routineID: String, exerciseID: String): Routine
    fun getRoutinesByCategory(category: String): List<Routine>
    fun searchRoutines(name: String, difficulty: String?): List<Routine>

}
