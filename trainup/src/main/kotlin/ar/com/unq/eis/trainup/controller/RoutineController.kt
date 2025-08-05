package ar.com.unq.eis.trainup.controller

import ar.com.unq.eis.trainup.controller.dto.BodyEjercicioDTO
import ar.com.unq.eis.trainup.controller.dto.BodyRutinaDTO
import ar.com.unq.eis.trainup.controller.dto.EjercicioDTO
import ar.com.unq.eis.trainup.controller.dto.RutinaDTO
import ar.com.unq.eis.trainup.services.RoutineService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/rutinas")
class RoutineController(
    @Autowired private val routineService: RoutineService
) {

    @PostMapping("/admin/crear")
    fun createRoutine(@RequestBody bodyRutinaDTO: BodyRutinaDTO): ResponseEntity<Any> {
        val newRoutine = routineService.createRoutine(bodyRutinaDTO.aModelo())
        return ResponseEntity.status(HttpStatus.CREATED).body(RutinaDTO.desdeModelo(newRoutine))
    }

    @GetMapping
    fun getPaginatedRoutines(
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "9") size: Int
    ): ResponseEntity<Any> {
        val routines = routineService.getPaginatedRoutines(PageRequest.of(page, size))
        return ResponseEntity.ok(routines)
    }

    @GetMapping("/{id}")
    fun getRoutineByID(@PathVariable id: String): ResponseEntity<Any> {
        val routine = routineService.getRoutinesById(id)
        return ResponseEntity.ok(RutinaDTO.desdeModelo(routine))
    }

    @PutMapping("/admin")
    fun updateRoutine(@RequestBody bodyRutinaDTO: BodyRutinaDTO): ResponseEntity<Any> {

        val updatedRoutine = routineService.updateRoutine(bodyRutinaDTO.aModelo())
        return ResponseEntity.ok(RutinaDTO.desdeModelo(updatedRoutine))
    }

    @DeleteMapping("/admin/{id}")
    fun deleteRoutine(@PathVariable id: String): ResponseEntity<Any> {
        routineService.deleteRoutine(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/admin/{id}/ejercicios")
    fun addExercise(@PathVariable id: String, @RequestBody exercise: BodyEjercicioDTO): ResponseEntity<Any> {
        val updatedRoutine = routineService.addExercise(id, exercise.aModelo())
        return ResponseEntity.ok(RutinaDTO.desdeModelo(updatedRoutine))
    }

    @PutMapping("/admin/{id}/ejercicio/actualizar")
    fun updateRoutineExercise(
        @PathVariable id: String,
        @RequestBody excercise: EjercicioDTO
    ): ResponseEntity<Any> {
        val udpdatedRoutine = routineService.addExercise(id, excercise.aModelo())
        return ResponseEntity.ok(RutinaDTO.desdeModelo(udpdatedRoutine))
    }

    @DeleteMapping("/admin/{id}/ejercicios/{idEj}")
    fun deleteExercise(@PathVariable id: String, @PathVariable idEj: String): ResponseEntity<Any> {
        val updatedRoutine = routineService.deleteExercise(id, idEj)
        return ResponseEntity.ok(RutinaDTO.desdeModelo(updatedRoutine))
    }

    @GetMapping("/categorias")
    fun getCategories(): ResponseEntity<Any> {
        val categories = mutableListOf("Cardio", "Fuerza", "Hipertrofia", "Funcional", "Resistencia")
        return ResponseEntity.ok(categories)
    }

    @GetMapping("/categoria/{category}")
    fun getRoutinesByCategory(@PathVariable category: String): ResponseEntity<Any> {
        val routines = routineService.getRoutinesByCategory(category)
        return ResponseEntity.ok(routines.map(RutinaDTO::desdeModelo))
    }

    @GetMapping("/buscar")
    fun buscarRutinas(
        @RequestParam(required = true) name: String,
        @RequestParam(required = false) difficulty: String?
    ): ResponseEntity<Any> {
        val routines = routineService.searchRoutines(name, difficulty);
        return ResponseEntity.ok(routines.map(RutinaDTO::desdeModelo))
    }
}
