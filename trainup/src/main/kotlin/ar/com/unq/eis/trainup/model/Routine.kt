package ar.com.unq.eis.trainup.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document(collection = "rutinas")
class Routine {

    @Id
    var id: String? = null
    var name: String = ""
    var img: String?=null
    var description: String = ""
    var category: String = ""
    var difficulty: String = ""
    var durationMinutes: Int = 0
    var objective: String = ""
    var weeklyFrequency: Int = 0
    var exercises: MutableList<Exercise> = mutableListOf()
    var creationDate: LocalDateTime = LocalDateTime.now()

    constructor()

    constructor(
        id: String?,
        name: String,
        img: String?,
        description: String,
        category: String,
        difficulty: String,
        durationMinutes: Int,
        objective: String,
        weeklyFrequency: Int,
        exercises: MutableList<Exercise> = mutableListOf()
    ) : this(
        name,
        img,
        description,
        category,
        exercises,
        difficulty
    ) {
        require(durationMinutes > 0) { "La duración debe ser mayor a 0 minutos" }
        require(weeklyFrequency > 0) { "La frecuencia semanal debe ser mayor a 0" }
        require(exercises.isNotEmpty()) { "La rutina debe contener al menos un ejercicio" }

        this.id = id
        this.durationMinutes = durationMinutes
        this.objective = objective
        this.weeklyFrequency = weeklyFrequency
    }


    constructor(
        name: String,
        img: String?,
        description: String,
        category: String,
        exercises: MutableList<Exercise> = mutableListOf(),
        difficulty: String
    ) {
        // Validaciones
        require(name.isNotEmpty()) { "El nombre de la rutina no puede estar vacía" }
        require(description.isNotEmpty()) { "La descripción de la rutina no puede estar vacía" }
        require(category.isNotEmpty()) { "La categoría no puede estar vacía" }
        require(difficulty.isNotEmpty()) { "La dificultad no puede estar vacía" }

        this.name = name
        this.img = img
        this.description = description
        this.difficulty = difficulty
        this.category = category
        this.exercises = exercises
        this.creationDate = LocalDateTime.now()
    }

    fun addExercise(exercise: Exercise) {

        //actualizar el ejercicio si existe, sino agregarlo
        val index = exercises.indexOfFirst { it.id == exercise.id }
        if (index != -1) {
            exercises[index] = exercise
        } else {
            exercises.add(exercise)
        }
    }

    fun deleteExercise(idEjercicio: String) {
        if(!exercises.removeIf{it.id == idEjercicio}){
            throw NoSuchElementException("No se encontró el ejercicio con id: $idEjercicio")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val routine = other as Routine?
        return id == routine!!.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)}

}
