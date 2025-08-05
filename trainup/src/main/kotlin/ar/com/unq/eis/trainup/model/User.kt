package ar.com.unq.eis.trainup.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.util.*

@Document(collection = "usuarios")
class User():UserDetails {

    @Id
    var id: String? = null

    @Indexed(unique = true)
    private var username: String = ""
    private var password: String = ""
<<<<<<< Updated upstream:trainup/src/main/kotlin/ar/com/unq/eis/trainup/model/Usuario.kt
    var rutinasSeguidas: MutableList<Rutina> = mutableListOf()
    var rutinasCompletadas: MutableList<Rutina> = mutableListOf()
    var nombre: String = ""
    var edad: Int? = null
    var fecNacimiento: LocalDate? = null
    var telefono: String = ""
    var genero: String = ""
    var altura: String = ""
    var peso: String = ""
    var objetivo: String = ""
    var rol: Role = Role.USER;
    var rutinasFavoritas: MutableList<Rutina> = mutableListOf()
    var ejerciciosCompletados: MutableList<EjercicioCompletado> = mutableListOf()
=======
    var followingRoutines: MutableList<Routine> = mutableListOf()
    var completedRoutines: MutableList<Routine> = mutableListOf()
    var name: String = ""
    var age: Int? = null
    var birthDate: LocalDate? = null
    var phone: String = ""
    var genre: String = ""
    var height: String = ""
    var weight: String = ""
    var objective: String = ""
    var role: Role = Role.USER;
    var favRoutines: MutableList<Routine> = mutableListOf()
    var completedExercises: MutableList<CompletedExercise> = mutableListOf()
>>>>>>> Stashed changes:trainup/src/main/kotlin/ar/com/unq/eis/trainup/model/User.kt

    constructor(
        username: String,
        password: String,
        name: String,
        age: Int,
        birthDate: LocalDate,
        phone: String,
        genre: String,
        height: String,
        weight: String,
        objective: String,
        role: Role = Role.USER
    ) : this() {
        require(username.isNotBlank()) { "El username no puede estar vacío" }
        require(password.isNotEmpty()) { "La contraseña no puede estar vacía" }
        require(name.isNotEmpty()) { "El nombre del usuario no puede estar vacío" }

        this.username = username
        this.password = password
<<<<<<< Updated upstream:trainup/src/main/kotlin/ar/com/unq/eis/trainup/model/Usuario.kt
        this.nombre = nombre
        this.edad = edad
        this.fecNacimiento = fecNacimiento
        this.telefono = telefono
        this.genero = genero
        this.altura = altura
        this.peso = peso
        this.objetivo = objetivo
        this.rol = rol
=======
        this.name = name
        this.age = age
        this.birthDate = birthDate
        this.phone = phone
        this.genre = genre
        this.height = height
        this.weight = weight
        this.objective = objective
        this.role = role
>>>>>>> Stashed changes:trainup/src/main/kotlin/ar/com/unq/eis/trainup/model/User.kt
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val user = other as User?
        return id == user!!.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(rol.name))
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun getUsername(): String {
        return this.username
    }

    fun setUsername(username:String){
        this.username = username
    }

    fun setPassword(password:String){
        this.password = password
    }

<<<<<<< Updated upstream:trainup/src/main/kotlin/ar/com/unq/eis/trainup/model/Usuario.kt
    fun completarRutina(idRutina: String): Boolean {

        val rutina = rutinasSeguidas.find { it.id == idRutina } // debe seguir a la rutina para poder completarla
=======
    fun completeRoutine(routineID: String) {

        val rutina = followingRoutines.find { it.id == routineID }?:
            throw RutinaNoSeguidaException(routineID, username)
>>>>>>> Stashed changes:trainup/src/main/kotlin/ar/com/unq/eis/trainup/model/User.kt

        if (rutina == null) return false

        if (!rutinasCompletadas.contains(rutina)) { // en caso de que no la haya completado previamente se agrega a su historial
            rutinasCompletadas.add(rutina)
        }

        return true
    }

<<<<<<< Updated upstream:trainup/src/main/kotlin/ar/com/unq/eis/trainup/model/Usuario.kt
    fun followUnfollowRutina(rutina: Rutina) {
        if (!rutinasSeguidas.removeIf { it.id == rutina.id }) {
            rutinasSeguidas.add(rutina)
        }
    }

    fun isFollowing(rutinaID: String): Boolean {
        return rutinasSeguidas.any { it.id == rutinaID }
    }

    fun completarEjercicio(idRutina: String, idEjercicio: String) {

        val rutina = rutinasSeguidas.find { it.id == idRutina } // debe seguir a la rutina
        val ejercicio = rutina?.ejercicios?.find { it.id == idEjercicio } // el ejercicio debe estar en la rutina

        if (ejercicio != null && !ejerciciosCompletados.any { it.ejercicioID == idEjercicio }) {
            ejerciciosCompletados.add(EjercicioCompletado(idRutina, idEjercicio))
=======
    fun toggleRoutineFollow(routine: Routine) {
        if (!followingRoutines.removeIf { it.id == routine.id }) {
            followingRoutines.add(routine)
        }
    }

    fun isFollowing(routineID: String): Boolean {
        return followingRoutines.any { it.id == routineID }
    }

    fun toggleExerciseCompletion(routineID: String, exerciseID: String) {

        val routine = followingRoutines.find { it.id == routineID }
            ?: throw RutinaNoSeguidaException(routineID, username)
        routine.exercises.find { it.id == exerciseID }
            ?: throw NoSuchElementException("No existe ejercicio con id ${exerciseID} en la rutina ${routineID}")

        if (!completedExercises.any { it.exerciseID == exerciseID }) {
            completedExercises.add(CompletedExercise(routineID, exerciseID))
>>>>>>> Stashed changes:trainup/src/main/kotlin/ar/com/unq/eis/trainup/model/User.kt
        }
        TODO("revisar el if" )

    }

<<<<<<< Updated upstream:trainup/src/main/kotlin/ar/com/unq/eis/trainup/model/Usuario.kt
    fun agregarONoRutinaFavorita(rutina: Rutina) {
        if (!rutinasFavoritas.removeIf { it.id == rutina.id }) {
            rutinasFavoritas.add(rutina)
=======
    fun addOrRemoveFavRoutine(routine: Routine) {
        if (!favRoutines.removeIf { it.id == routine.id }) {
            favRoutines.add(routine)
>>>>>>> Stashed changes:trainup/src/main/kotlin/ar/com/unq/eis/trainup/model/User.kt
        }
    }
}
