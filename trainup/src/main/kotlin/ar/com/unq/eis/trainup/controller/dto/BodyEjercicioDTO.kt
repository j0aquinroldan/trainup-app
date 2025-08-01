package ar.com.unq.eis.trainup.controller.dto

import ar.com.unq.eis.trainup.model.Ejercicio

class BodyEjercicioDTO(
        var nombre: String = "",
        var descripcion: String = "",
        var repeticiones: Int = 0,
        var peso: Double = 0.0,
        var musculo: String = ""
    )
{
        companion object {
            fun desdeModelo(ejercicio: Ejercicio): EjercicioDTO {
                return EjercicioDTO(
                    nombre = ejercicio.nombre,
                    descripcion = ejercicio.descripcion,
                    repeticiones = ejercicio.repeticiones,
                    peso = ejercicio.peso,
                    musculo = ejercicio.musculo
                )
            }
        }

        fun aModelo(): Ejercicio {
            return Ejercicio(
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