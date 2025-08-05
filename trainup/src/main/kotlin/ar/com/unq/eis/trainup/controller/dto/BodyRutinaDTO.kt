package ar.com.unq.eis.trainup.controller.dto

import ar.com.unq.eis.trainup.model.Routine

class BodyRutinaDTO(
    var id: String? = null,
    var name: String = "",
    var img: String = "",
    var description: String = "",
    var category: String = "",
    var difficulty: String = ""
) {
    fun aModelo(): Routine {

        return Routine(
            name = this.name,
            img = this.img,
            description = this.description,
            category = this.category,
            exercises = mutableListOf(),
            difficulty = this.difficulty
        ).also { it.id = this.id }
    }
}
