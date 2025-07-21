//package ar.com.unq.eis.trainup.controller
//
//import EjercicioService
//import ar.com.unq.eis.trainup.controller.dto.EjercicioDTO
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.http.HttpStatus
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.*
//
//@RestController
//@RequestMapping("/api/ejercicios")
//class EjercicioController(
//    @Autowired private val ejercicioService: EjercicioService
//) {
//
//    @PostMapping
//    fun crearEjercicio(@RequestBody ejercicioDTO: EjercicioDTO): ResponseEntity<Any> {
//        val nuevoEjercicio = ejercicioService.crearEjercicio(ejercicioDTO.aModelo())
//        return ResponseEntity.status(HttpStatus.CREATED).body(EjercicioDTO.desdeModelo(nuevoEjercicio))
//    }
//
//
//    @GetMapping
//    fun obtenerEjercicios(): ResponseEntity<Any> {
//
//        val ejercicios = ejercicioService.obtenerEjercicios().map { e ->
//            EjercicioDTO.desdeModelo(e)
//        }
//        return ResponseEntity.ok(ejercicios)
//    }
//
//    @GetMapping("/{id}")
//    fun obtenerEjercicioPorId(@PathVariable id: String): ResponseEntity<Any> {
//        val ejercicio = EjercicioDTO.desdeModelo(ejercicioService.obtenerEjercicioPorId(id))
//        return ResponseEntity.ok(ejercicio)
//    }
//
//    @PutMapping("/actualizar")
//    fun actualizarEjercicio(@RequestBody ejercicioDTO: EjercicioDTO): ResponseEntity<Any> {
//        val ejercicio = ejercicioService.actualizarEjercicio(ejercicioDTO.aModelo())
//        return ResponseEntity.ok(EjercicioDTO.desdeModelo(ejercicio))
//    }
//
//    @DeleteMapping("/{id}")
//    fun eliminarEjercicio(@PathVariable id: String): ResponseEntity<Any> {
//
//        ejercicioService.eliminarEjercicio(id)
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
//
//    }
//}
