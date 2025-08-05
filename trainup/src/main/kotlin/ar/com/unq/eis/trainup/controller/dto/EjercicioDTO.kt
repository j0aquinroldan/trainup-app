package ar.com.unq.eis.trainup.controller.dto

import ar.com.unq.eis.trainup.model.Exercise

 class EjercicioDTO(
    var id: String? = null,
    var nombre: String = "",
    var descripcion: String = "",
    var repeticiones: Int = 0,
    var peso: Double = 0.0,
    var musculo: String = "",
    var series: Int = 0,
    var descansoSegundos: Int = 0,
    var equipo: String = "",
    var instrucciones: String = ""
) {

    companion object {
        fun desdeModelo(exercise: Exercise): EjercicioDTO {
            return EjercicioDTO(
                id = exercise.id,
                nombre = exercise.nombre,
                descripcion = exercise.descripcion,
                repeticiones = exercise.repeticiones,
                peso = exercise.peso,
                musculo = exercise.musculo,
                series = exercise.series,
                descansoSegundos = exercise.descansoSegundos,
                equipo = exercise.equipo,
                instrucciones = exercise.instrucciones
            )
        }
    }

    fun aModelo(): Exercise {
        return Exercise(
            id = this.id,
            nombre = this.nombre,
            descripcion = this.descripcion,
            repeticiones = this.repeticiones,
            peso = this.peso,
            musculo = this.musculo,
            series = this.series,
            descansoSegundos = this.descansoSegundos,
            equipo = this.equipo,
            instrucciones = this.instrucciones
        )
    }
}
