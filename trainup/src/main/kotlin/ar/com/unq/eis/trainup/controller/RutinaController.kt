package ar.com.unq.eis.trainup.controller

import ar.com.unq.eis.trainup.controller.dto.BodyEjercicioDTO
import ar.com.unq.eis.trainup.controller.dto.BodyRutinaDTO
import ar.com.unq.eis.trainup.controller.dto.EjercicioDTO
import ar.com.unq.eis.trainup.controller.dto.RutinaDTO
import ar.com.unq.eis.trainup.services.RutinaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/rutinas")
class RutinaController(
    @Autowired private val rutinaService: RutinaService
) {

    @PostMapping("/admin/crear")
    fun crearRutina(@RequestBody bodyRutinaDTO: BodyRutinaDTO): ResponseEntity<Any> {
        val nuevaRutina = rutinaService.crearRutina(bodyRutinaDTO.aModelo())
        return ResponseEntity.status(HttpStatus.CREATED).body(RutinaDTO.desdeModelo(nuevaRutina))
    }

    @GetMapping
    fun obtenerRutinasPaginadas(
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "9") size: Int
    ): ResponseEntity<Any> {
        val rutinas = rutinaService.obtenerRutinasPag(PageRequest.of(page, size))
        return ResponseEntity.ok(rutinas)
    }

    @GetMapping("/{id}")
    fun obtenerRutinaPorId(@PathVariable id: String): ResponseEntity<Any> {
        val rutina = rutinaService.obtenerRutinaPorId(id)
        return ResponseEntity.ok(RutinaDTO.desdeModelo(rutina))
    }

    @PutMapping("/admin")
    fun actualizarRutina(@RequestBody bodyRutinaDTO: BodyRutinaDTO): ResponseEntity<Any> {

        val rutinaActualizada = rutinaService.actualizarRutina(bodyRutinaDTO.aModelo())
        return ResponseEntity.ok(RutinaDTO.desdeModelo(rutinaActualizada))
    }

    @DeleteMapping("/admin/{id}")
    fun eliminarRutina(@PathVariable id: String): ResponseEntity<Any> {
        rutinaService.eliminarRutina(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/admin/{id}/ejercicios")
    fun agregarEjercicio(@PathVariable id: String, @RequestBody ejercicio: BodyEjercicioDTO): ResponseEntity<Any> {
        val rutinaActualizada = rutinaService.agregarEjercicio(id, ejercicio.aModelo())
        return ResponseEntity.ok(RutinaDTO.desdeModelo(rutinaActualizada))
    }

    @PutMapping("/admin/{id}/ejercicio/actualizar")
    fun actualizarEjercicioEnRutina(
        @PathVariable id: String,
        @RequestBody ejercicio: EjercicioDTO
    ): ResponseEntity<Any> {
        val rutinaActualizada = rutinaService.agregarEjercicio(id, ejercicio.aModelo())
        return ResponseEntity.ok(RutinaDTO.desdeModelo(rutinaActualizada))
    }

    @DeleteMapping("/admin/{id}/ejercicios/{idEj}")
    fun eliminarEjercicio(@PathVariable id: String, @PathVariable idEj: String): ResponseEntity<Any> {
        val rutinaActualizada = rutinaService.eliminarEjercicio(id, idEj)
        return ResponseEntity.ok(RutinaDTO.desdeModelo(rutinaActualizada))
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
        val rutinas = rutinaService.buscarRutinas(nombre, dificultad);
        return ResponseEntity.ok(rutinas.map(RutinaDTO::desdeModelo))
    }
}
