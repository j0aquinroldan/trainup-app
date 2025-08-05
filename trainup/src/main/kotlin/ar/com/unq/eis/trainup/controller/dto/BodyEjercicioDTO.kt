package ar.com.unq.eis.trainup.controller.dto

import ar.com.unq.eis.trainup.model.Exercise

class BodyEjercicioDTO(
        var nombre: String = "",
        var descripcion: String = "",
        var repeticiones: Int = 0,
        var peso: Double = 0.0,
        var musculo: String = ""
    )
{
        companion object {
            fun desdeModelo(exercise: Exercise): EjercicioDTO {
                return EjercicioDTO(
                    nombre = exercise.nombre,
                    descripcion = exercise.descripcion,
                    repeticiones = exercise.repeticiones,
                    peso = exercise.peso,
                    musculo = exercise.musculo
                )
            }
        }

        fun aModelo(): Exercise {
            return Exercise(
                nombre = this.nombre,
                descripcion = this.descripcion,
                repeticiones = this.repeticiones,
                peso = this.peso,
                musculo = this.musculo,
                series = 1,
                descansoSegundos = 0,
                equipo = "",
                instrucciones = ""
            )
        }
    }