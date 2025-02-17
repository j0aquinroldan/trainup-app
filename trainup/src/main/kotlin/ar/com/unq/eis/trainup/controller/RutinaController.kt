package ar.com.unq.eis.trainup.controller

import ar.com.unq.eis.trainup.controller.Exceptions.RutinaException
import ar.com.unq.eis.trainup.controller.dto.*
import ar.com.unq.eis.trainup.services.RutinaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/rutinas")
class RutinaController(
    @Autowired private val rutinaService: RutinaService
) {

    @PostMapping
    fun crearRutina(@RequestBody bodyRutinaDTO: BodyRutinaDTO): ResponseEntity<Any> {
        return try {
            val nuevaRutina = rutinaService.crearRutina(bodyRutinaDTO.aModelo())
            ResponseEntity.status(HttpStatus.CREATED).body(RutinaDTO.desdeModelo(nuevaRutina))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDTO(Exception("El body de la rutina no es válido")))
        }
    }

    @GetMapping
    fun obtenerRutinas(): ResponseEntity<Any> {
        val rutinas = rutinaService.obtenerRutinas()
        return ResponseEntity.ok(rutinas.map(RutinaDTO::desdeModelo))
    }

    @GetMapping("/{id}")
    fun obtenerRutinaPorId(@PathVariable id: String): ResponseEntity<Any> {
        return try {
            rutinaService.obtenerRutinaPorId(id)?.let { rutina ->
                ResponseEntity.ok(RutinaDTO.desdeModelo(rutina))
            } ?: ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDTO(Exception("Rutina no encontrada")))
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDTO(e))
        }
    }

    @PutMapping
    fun actualizarRutina(@RequestBody bodyRutinaDTO: BodyRutinaDTO): ResponseEntity<Any> {
        return try {
            val rutinaActualizada = rutinaService.actualizarRutina(bodyRutinaDTO.aModelo())
            ResponseEntity.ok(RutinaDTO.desdeModelo(rutinaActualizada))
        } catch (e: RutinaException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDTO(e))
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDTO(e))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorDTO(e))
        }
    }

    @DeleteMapping("/{id}")
    fun eliminarRutina(@PathVariable id: String): ResponseEntity<Any> {
        return try {
            rutinaService.eliminarRutina(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDTO(e))
        }
    }

    @PostMapping("/{id}/ejercicios")
    fun agregarEjercicio(@PathVariable id: String, @RequestBody ejercicio: BodyEjercicioDTO): ResponseEntity<Any> {
        return try {
            val rutinaActualizada = rutinaService.agregarEjercicio(id, ejercicio.aModelo())
            ResponseEntity.ok(RutinaDTO.desdeModelo(rutinaActualizada))
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDTO(e))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDTO(e))
        }
    }

    @PutMapping("/{id}/ejercicio/actualizar")
    fun actualizarEjercicioEnRutina(
        @PathVariable id: String,
        @RequestBody ejercicio: EjercicioDTO
    ): ResponseEntity<Any> {
        return try {
            val rutinaActualizada = rutinaService.agregarEjercicio(id, ejercicio.aModelo())
            ResponseEntity.ok(RutinaDTO.desdeModelo(rutinaActualizada))
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDTO(e))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDTO(e))
        }
    }

    @DeleteMapping("/{id}/ejercicios/{idEj}")
    fun eliminarEjercicio(@PathVariable id: String, @PathVariable idEj: String): ResponseEntity<Any> {
        return try {
            val rutinaActualizada = rutinaService.eliminarEjercicio(id, idEj)
            ResponseEntity.ok(RutinaDTO.desdeModelo(rutinaActualizada))
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDTO(e))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDTO(e))
        }
    }

    @GetMapping("/categorias")
    fun obtenerCategorias(): ResponseEntity<Any> {
        val categorias = mutableListOf("Cardio", "Fuerza", "Hipertrofia", "Funcional", "Resistencia")
        return ResponseEntity.ok(categorias)
    }

    @GetMapping("/categoria/{categoria}")
    fun obtenerRutinasPorCategoria(@PathVariable categoria: String): ResponseEntity<Any> {
        val rutinas = rutinaService.obtenerRutinasPorCategoria(categoria)
        return ResponseEntity.ok(rutinas.map(RutinaDTO::desdeModelo))
    }

    @GetMapping("/buscar")
    fun buscarRutinas(
        @RequestParam(required = true) nombre: String,
        @RequestParam(required = false) dificultad: String?
    ): ResponseEntity<Any> {
        return try {
            val rutinas = rutinaService.buscarRutinas(nombre, dificultad);
            ResponseEntity.ok(rutinas.map(RutinaDTO::desdeModelo))
        } catch (e: RutinaException) {
            ResponseEntity.internalServerError().body(ErrorDTO(e))
        }
    }
}
