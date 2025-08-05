package ar.com.unq.eis.trainup.controller.dto

import ar.com.unq.eis.trainup.model.Routine
import java.time.LocalDateTime

data class RutinaDTO(
    var id: String? = null,
    var name: String = "",
    var img: String?=null,
    var description: String = "",
    var category: String = "",
    var dificulty: String = "",
    var durationMinutes: Int = 0,
    var objective: String = "",
    var weeklyFrequency: Int = 0,
    var creationDate: String = "",
    var exercises: MutableList<EjercicioDTO> = mutableListOf()
) {

    companion object {
        fun desdeModelo(routine: Routine): RutinaDTO {
            return RutinaDTO(
                id = routine.id,
                name = routine.name,
                img = routine.img,
                description = routine.description,
                category = routine.category,
                dificulty = routine.difficulty,
                durationMinutes = routine.durationMinutes,
                objective = routine.objective,
                weeklyFrequency = routine.weeklyFrequency,
                creationDate = routine.creationDate.toString(),
                exercises = routine.exercises.map { EjercicioDTO.desdeModelo(it) }.toMutableList()
            )
        }
    }

    fun aModelo(): Routine {
        return Routine(
            id = this.id,
            name = this.name,
            img= this.img,
            description = this.description,
            category = this.category,
            difficulty = this.dificulty,
            durationMinutes = this.durationMinutes,
            objective = this.objective,
            weeklyFrequency = this.weeklyFrequency,
            exercises = this.exercises.map { it.aModelo() }.toMutableList()
        )
            .also { it.creationDate = LocalDateTime.parse(this.creationDate) }
    }

}
