<<<<<<< Updated upstream:trainup/src/test/kotlin/ar/com/unq/eis/trainup/modeloTest/UsuarioTest.kt
//package ar.com.unq.eis.trainup.modeloTest
//
//import ar.com.unq.eis.trainup.controller.Exceptions.UsuarioException
//import ar.com.unq.eis.trainup.model.Ejercicio
//import ar.com.unq.eis.trainup.model.Rutina
//import ar.com.unq.eis.trainup.model.Usuario
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertThrows
//import java.time.LocalDate
//import kotlin.test.assertEquals
//
//class UsuarioTest {
//
//    private lateinit var ejercicios: MutableList<Ejercicio>
//    private lateinit var rutina: Rutina
//    private lateinit var usuario: Usuario
//
//    @BeforeEach
//    fun setup() {
//        usuario = Usuario(
//            username = "userMock",
//            password = "123",
//            nombre = "user mock",
//            edad = 20,
//            fecNacimiento = LocalDate.of(2004, 9, 12),
//            telefono = "1234567890",
//            genero = "masculino",
//            altura = "170",
//            peso = "60",
//            objetivo = "ganar musculatura",
//            esAdmin = false
//        )
//
//        ejercicios = mutableListOf(
//            Ejercicio(
//                nombre = "Sprints en cinta",
//                descripcion = "Sprints a máxima velocidad",
//                repeticiones = 10,
//                peso = 0.0,
//                musculo = "Piernas",
//                series = 3,
//                descansoSegundos = 30,
//                equipo = "Cinta",
//                instrucciones = "Corre a máxima velocidad por 30 segundos"
//            ),
//            Ejercicio(
//                nombre = "Salto con rodillas al pecho",
//                descripcion = "Salto explosivo con rodillas al pecho",
//                repeticiones = 15,
//                peso = 0.0,
//                musculo = "Piernas",
//                series = 3,
//                descansoSegundos = 30,
//                equipo = "Ninguno",
//                instrucciones = "Salta lo más alto que puedas"
//            )
//        )
//
//        rutina = Rutina(
//            nombre = "Rutina HIIT",
//            descripcion = "Entrenamiento de intervalos de alta intensidad",
//            categoria = "HIIT",
//            ejercicios = ejercicios.toMutableList()
//        )
//
//    }
//
//    @Test
//    fun `constructor del usuario es correcto`() {
//        assertEquals("userMock", usuario.username)
//        assertEquals("123", usuario.password)
//        assertEquals("user mock", usuario.nombre)
//        assertEquals(20, usuario.edad)
//        assertEquals(LocalDate.of(2004, 9, 12), usuario.fecNacimiento)
//        assertEquals("1234567890", usuario.telefono)
//        assertEquals("masculino", usuario.genero)
//        assertEquals("170", usuario.altura)
//        assertEquals("60", usuario.peso)
//        assertEquals("ganar musculatura", usuario.objetivo)
//        assertFalse(usuario.esAdmin)  // Si esAdmin es falso por defecto
//    }
//
//    @Test
//    fun `username no puede estar en blanco`() {
//        val exception = assertThrows<IllegalArgumentException> {
//            Usuario(
//                username = "",
//                password = "123",
//                nombre = "user mock",
//                edad = 20,
//                fecNacimiento = LocalDate.of(2004, 9, 12),
//                telefono = "1234567890",
//                genero = "masculino",
//                altura = "170",
//                peso = "60",
//                objetivo = "ganar musculatura"
//            )
//        }
//        assertEquals("El username no puede estar vacío", exception.message)
//    }
//
//    @Test
//    fun `password no puede estar en blanco`() {
//        val exception = assertThrows<IllegalArgumentException> {
//            Usuario(
//                username = "userMock",
//                password = "",
//                nombre = "user mock",
//                edad = 20,
//                fecNacimiento = LocalDate.of(2004, 9, 12),
//                telefono = "1234567890",
//                genero = "masculino",
//                altura = "170",
//                peso = "60",
//                objetivo = "ganar musculatura"
//            )
//        }
//        assertEquals("La contraseña no puede estar vacía", exception.message)
//    }
//
//    @Test
//    fun `nombre no puede estar en blanco`() {
//        val exception = assertThrows<IllegalArgumentException> {
//            Usuario(
//                username = "userMock",
//                password = "123",
//                nombre = "",
//                edad = 20,
//                fecNacimiento = LocalDate.of(2004, 9, 12),
//                telefono = "1234567890",
//                genero = "masculino",
//                altura = "170",
//                peso = "60",
//                objetivo = "ganar musculatura"
//            )
//        }
//        assertEquals("El nombre no puede estar vacío", exception.message)
//    }
//
//    @Test
//    fun `completar rutina exitoso`() {
//        usuario.rutinasSeguidas.add(rutina)
//
//        assertEquals(mutableListOf(rutina), usuario.rutinasSeguidas)
//        assertEquals(mutableListOf<Rutina>(), usuario.rutinasCompletadas)
//
//        usuario.completarRutina(rutina)
//
//        assertEquals(mutableListOf(rutina), usuario.rutinasCompletadas)
//        assertEquals(mutableListOf<Rutina>(), usuario.rutinasSeguidas)
//    }
//
//    @Test
//    fun `completar rutina fallido`() {
//        val exception = assertThrows<UsuarioException> {
//            usuario.completarRutina(rutina)
//        }
//        assertEquals("El usuario no sigue a dicha rutina", exception.message)
//    }
//
//    @Test
//    fun `seguir rutina`() {
//        usuario.followUnfollowRutina(rutina)
//        assertEquals(mutableListOf(rutina), usuario.rutinasSeguidas)
//    }
//}
=======
package ar.com.unq.eis.trainup.modelTest

import ar.com.unq.eis.trainup.controller.Exceptions.RutinaNoSeguidaException
import ar.com.unq.eis.trainup.model.Exercise
import ar.com.unq.eis.trainup.model.Role
import ar.com.unq.eis.trainup.model.Routine
import ar.com.unq.eis.trainup.model.User
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.util.NoSuchElementException
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class UserTest {

    private lateinit var exercises: MutableList<Exercise>
    private lateinit var routine: Routine
    private lateinit var user: User

    @BeforeEach
    fun setup() {
        user = User(
            username = "testUser",
            password = "123",
            nombre = "testUser",
            edad = 20,
            fecNacimiento = LocalDate.of(2004, 9, 12),
            telefono = "1234567890",
            genero = "masculino",
            altura = "170",
            peso = "60",
            objetivo = "ganar musculatura"
        )


        exercises = mutableListOf(
            Exercise(
                id = "1",
                nombre = "Sprints en cinta",
                descripcion = "Sprints a máxima velocidad",
                repeticiones = 10,
                peso = 0.0,
                musculo = "Piernas",
                series = 3,
                descansoSegundos = 30,
                equipo = "Cinta",
                instrucciones = "Corre a máxima velocidad por 30 segundos"
            ),
            Exercise(
                id= "2",
                nombre = "Salto con rodillas al pecho",
                descripcion = "Salto explosivo con rodillas al pecho",
                repeticiones = 15,
                peso = 0.0,
                musculo = "Piernas",
                series = 3,
                descansoSegundos = 30,
                equipo = "Ninguno",
                instrucciones = "Salta lo más alto que puedas"
            )
        )

        routine = Routine(
            name = "Rutina HIIT",
            img = null,
            description = "Entrenamiento de intervalos de alta intensidad",
            category = "HIIT",
            exercises = exercises,
            difficulty = "Alta"
        )
        routine.id = "1"
    }

    @Test
    fun `constructor del usuario es correcto`() {
        assertEquals("testUser", user.username)
        assertEquals("123", user.password)
        assertEquals("testUser", user.name)
        assertEquals(20, user.age)
        assertEquals(LocalDate.of(2004, 9, 12), user.birthDate)
        assertEquals("1234567890", user.phone)
        assertEquals("masculino", user.genre)
        assertEquals("170", user.height)
        assertEquals("60", user.weight)
        assertEquals("ganar musculatura", user.objective)
        assertEquals(user.role, Role.USER)  // Si esAdmin es falso por defecto
    }

    @Test
    fun `username no puede estar en blanco`() {
        val exception = assertThrows<IllegalArgumentException> {
            User(
                username = "",
                password = "123",
                nombre = "user mock",
                edad = 20,
                fecNacimiento = LocalDate.of(2004, 9, 12),
                telefono = "1234567890",
                genero = "masculino",
                altura = "170",
                peso = "60",
                objetivo = "ganar musculatura"
            )
        }
        assertEquals("El username no puede estar vacío", exception.message)
    }

    @Test
    fun `password no puede estar en blanco`() {
        val exception = assertThrows<IllegalArgumentException> {
            User(
                username = "userMock",
                password = "",
                nombre = "user mock",
                edad = 20,
                fecNacimiento = LocalDate.of(2004, 9, 12),
                telefono = "1234567890",
                genero = "masculino",
                altura = "170",
                peso = "60",
                objetivo = "ganar musculatura"
            )
        }
        assertEquals("La contraseña no puede estar vacía", exception.message)
    }

    @Test
    fun `nombre no puede estar en blanco`() {
        val exception = assertThrows<IllegalArgumentException> {
            User(
                username = "userMock",
                password = "123",
                nombre = "",
                edad = 20,
                fecNacimiento = LocalDate.of(2004, 9, 12),
                telefono = "1234567890",
                genero = "masculino",
                altura = "170",
                peso = "60",
                objetivo = "ganar musculatura"
            )
        }
        assertEquals("El nombre del usuario no puede estar vacío", exception.message)
    }

    @Test
    fun `al seguir rutina se agrega a rutinasSeguidas de usuario`() {
        user.toggleRoutineFollow(routine)
        assertEquals(mutableListOf(routine), user.followingRoutines)
    }

    @Test
    fun `si ya sigue a la rutina se elimina de rutinasSeguidas`() {
        user.toggleRoutineFollow(routine)
        user.toggleRoutineFollow(routine) // Intento de seguir la misma rutina nuevamente
        assertEquals(mutableListOf(), user.followingRoutines)
    }

    @Test
    fun `completar rutina exitoso`() {
        user.toggleRoutineFollow(routine)

        assertEquals(mutableListOf(routine), user.followingRoutines)
        assertEquals(mutableListOf<Routine>(), user.completedRoutines)

        user.completeRoutine("1")

        assertEquals(mutableListOf(routine), user.completedRoutines)
        assertEquals(mutableListOf(routine), user.followingRoutines)
    }

    @Test
    fun `completar rutina fallido`() {
        val exception = assertThrows<RutinaNoSeguidaException> {
            user.completeRoutine("1")
        }
        assertEquals("El usuario: testUser no sigue a rutina 1", exception.message)
    }

    @Test
    fun `usuario equals`(){
        val otroUser = User(
            username = "testUser",
            password = "123",
            nombre = "testUser",
            edad = 20,
            fecNacimiento = LocalDate.of(2004, 9, 12),
            telefono = "1234567890",
            genero = "masculino",
            altura = "170",
            peso = "60",
            objetivo = "ganar musculatura"
        )

        assertEquals(user, otroUser)
    }

    @Test
    fun `usuario no es igual a otro objeto`() {
        val otroObjeto = "No soy un usuario"
        assert(!user.equals(otroObjeto))
    }

    @Test
    fun `usuario no es igual a null`() {
        assert(!user.equals(null))
    }

    @Test
    fun `usuario no es igual a otro usuario con diferente id`() {
        val otroUser = User(
            username = "testUser",
            password = "123",
            nombre = "testUser",
            edad = 20,
            fecNacimiento = LocalDate.of(2004, 9, 12),
            telefono = "1234567890",
            genero = "masculino",
            altura = "170",
            peso = "60",
            objetivo = "ganar musculatura"
        )
        otroUser.id = "2" // Diferente ID
        assert(!user.equals(otroUser))
    }

    @Test
    fun `user follows routine`(){
        user.toggleRoutineFollow(routine)
        assert(user.isFollowing(routine.id!!))
    }

    @Test
    fun `user does not follow routine`(){
        assert(!user.isFollowing(routine.id!!))
    }

    @Test
    fun `user unfollows routine after following it`(){
        user.toggleRoutineFollow(routine)
        user.toggleRoutineFollow(routine)
        assertFalse(user.isFollowing(routine.id!!))
    }

    @Test
    fun `addOrRemoveFavRoutine adds routine to favRoutines when not present`() {
        user.addOrRemoveFavRoutine(routine)
        assert(user.favRoutines.contains(routine))
    }

    @Test
    fun `addOrRemoveFavRoutine removes routine from favRoutines when already present`() {
        user.addOrRemoveFavRoutine(routine)
        user.addOrRemoveFavRoutine(routine)
        assert(!user.favRoutines.contains(routine))
    }

    @Test
    fun `completeExercise throws RutinaNoSeguidaException when does not follow routine`() {
        val exception = assertThrows<RutinaNoSeguidaException> {
            user.toggleExerciseCompletion("54", "54")
        }

        assertEquals("El usuario: testUser no sigue a rutina 54", exception.message)
    }

    @Test
    fun `completeExercise adds exercise to completedExercises when user follows routine`() {
        user.toggleRoutineFollow(routine)
        user.toggleExerciseCompletion(routine.id!!, exercises[0].id!!)

        assertEquals(1, user.completedExercises.size)
        assertEquals(exercises[0].id, user.completedExercises[0].ejercicioID)
    }

    @Test
    fun `completeExercise does not add exercise to completedExercises when already completed`() {
        user.toggleRoutineFollow(routine)
        user.toggleExerciseCompletion(routine.id!!, exercises[0].id!!)

        user.toggleExerciseCompletion(routine.id!!, exercises[0].id!!)

        assertEquals(1, user.completedExercises.size)
    }

    @Test
    fun `completeExercise throws NoSuchElementException when exercise does not exists in routine`() {
        user.toggleRoutineFollow(routine)
        val exception = assertThrows<NoSuchElementException> {  user.toggleExerciseCompletion(routine.id!!, "500")}

        assertEquals("No existe ejercicio con id 500 en la rutina 1", exception.message)
    }

    @Test
    fun `setUsername updates username correctly`() {
        user.setUsername("newUsername")
        assertEquals("newUsername", user.username)
    }

    @Test
    fun `setPassword updates password correctly`() {
        user.setPassword("newPassword")
        assertEquals("newPassword", user.password)
    }

}
>>>>>>> Stashed changes:trainup/src/test/kotlin/ar/com/unq/eis/trainup/modelTest/UsuarioTest.kt
