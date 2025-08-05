package ar.com.unq.eis.trainup.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "ejercicios")
class Exercise {

    @Id
    var id: String? = null
    var nombre: String = ""
    var descripcion: String = ""
    var repeticiones: Int = 0
    var peso: Double = 0.0
    var musculo: String = ""
    var series: Int = 0
    var descansoSegundos: Int = 0
    var equipo: String = ""
    var instrucciones: String = ""

    constructor()

    constructor(
        id: String? = null,
        nombre: String,
        descripcion: String,
        repeticiones: Int,
        peso: Double,
        musculo: String,
        series: Int,
        descansoSegundos: Int,
        equipo: String,
        instrucciones: String
    ) {
        // Validaciones
        require(nombre.isNotEmpty()) { "El nombre del ejercicio no puede estar vacío" }
        require(descripcion.isNotEmpty()) { "La descripción del ejercicio no puede estar vacía" }
        require(repeticiones > 0) { "Las repeticiones deben ser mayores a 0" }
        require(peso >= 0) { "El peso no puede ser negativo" }
        require(musculo.isNotEmpty()) { "El nombre del músculo no puede estar vacío" }
        require(series > 0) { "Las series deben ser mayores a 0" }
        require(descansoSegundos >= 0) { "El tiempo de descanso no puede ser negativo" }

        this.id = id
        this.nombre = nombre
        this.descripcion = descripcion
        this.repeticiones = repeticiones
        this.peso = peso
        this.musculo = musculo
        this.series = series
        this.descansoSegundos = descansoSegundos
        this.equipo = equipo
        this.instrucciones = instrucciones
    }
}
