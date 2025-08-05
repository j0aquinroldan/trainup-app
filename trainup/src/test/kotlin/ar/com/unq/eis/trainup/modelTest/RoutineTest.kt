package ar.com.unq.eis.trainup.modelTest

import ar.com.unq.eis.trainup.model.Exercise
import ar.com.unq.eis.trainup.model.Routine
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RoutineTest {

    private fun sampleExercise() = Exercise(
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
    )

    @Test
    fun `should create valid routine with short constructor`() {
        val routine = Routine(
            name = "Basic Routine",
            img = null,
            description = "Beginner-friendly routine",
            category = "Strength",
            exercises = mutableListOf(sampleExercise()),
            difficulty = "Beginner"
        )

        assertEquals("Basic Routine", routine.name)
    }

    @Test
    fun `should throw exception when name is blank in short constructor`() {
        val ex = assertThrows<IllegalArgumentException> {
            Routine(
                name = "",
                img = null,
                description = "desc",
                category = "cat",
                exercises = mutableListOf(sampleExercise()),
                difficulty = "diff"
            )
        }
        assertEquals("El nombre de la rutina no puede estar vacía", ex.message)
    }

    @Test
    fun `should throw exception when description is blank in short constructor`() {
        val ex = assertThrows<IllegalArgumentException> {
            Routine(
                name = "Name",
                img = null,
                description = "",
                category = "cat",
                exercises = mutableListOf(sampleExercise()),
                difficulty = "diff"
            )
        }
        assertEquals("La descripción de la rutina no puede estar vacía", ex.message)
    }

    @Test
    fun `should throw exception when category is blank in short constructor`() {
        val ex = assertThrows<IllegalArgumentException> {
            Routine(
                name = "Name",
                img = null,
                description = "desc",
                category = "",
                exercises = mutableListOf(sampleExercise()),
                difficulty = "diff"
            )
        }
        assertEquals("La categoría no puede estar vacía", ex.message)
    }

    @Test
    fun `should throw exception when difficulty is blank in short constructor`() {
        val ex = assertThrows<IllegalArgumentException> {
            Routine(
                name = "Name",
                img = null,
                description = "desc",
                category = "cat",
                exercises = mutableListOf(sampleExercise()),
                difficulty = ""
            )
        }
        assertEquals("La dificultad no puede estar vacía", ex.message)
    }

    // -------- Constructor with all parameters --------

    @Test
    fun `should create valid routine with full constructor`() {
        val routine = Routine(
            id = null,
            name = "Complete Routine",
            img = null,
            description = "Advanced strength routine",
            category = "Strength",
            difficulty = "Advanced",
            durationMinutes = 60,
            objective = "Hypertrophy",
            weeklyFrequency = 4,
            exercises = mutableListOf(sampleExercise())
        )

        assertEquals("Complete Routine", routine.name)
        assertEquals(60, routine.durationMinutes)
    }

    @Test
    fun `should throw exception when duration is 0 in full constructor`() {
        val ex = assertThrows<IllegalArgumentException> {
            Routine(
                id = null,
                name = "Name",
                img = null,
                description = "desc",
                category = "cat",
                difficulty = "diff",
                durationMinutes = 0,
                objective = "obj",
                weeklyFrequency = 3,
                exercises = mutableListOf(sampleExercise())
            )
        }
        assertEquals("La duración debe ser mayor a 0 minutos", ex.message)
    }

    @Test
    fun `should throw exception when weekly frequency is 0 in full constructor`() {
        val ex = assertThrows<IllegalArgumentException> {
            Routine(
                id = null,
                name = "Name",
                img = null,
                description = "desc",
                category = "cat",
                difficulty = "diff",
                durationMinutes = 30,
                objective = "obj",
                weeklyFrequency = 0,
                exercises = mutableListOf(sampleExercise())
            )
        }
        assertEquals("La frecuencia semanal debe ser mayor a 0", ex.message)
    }

    @Test
    fun `should throw exception when exercises list is empty in full constructor`() {
        val ex = assertThrows<IllegalArgumentException> {
            Routine(
                id = null,
                name = "Name",
                img = null,
                description = "desc",
                category = "cat",
                difficulty = "diff",
                durationMinutes = 30,
                objective = "obj",
                weeklyFrequency = 3,
                exercises = mutableListOf()
            )
        }
        assertEquals("La rutina debe contener al menos un ejercicio", ex.message)
    }

    @Test
    fun `should add exercise`(){
        val routine = Routine(
            name = "Routine with Exercises",
            img = null,
            description = "Routine with multiple exercises",
            category = "Cardio",
            exercises = mutableListOf(),
            difficulty = "Intermediate"
        )

        routine.addExercise(sampleExercise())

        assertEquals(1, routine.exercises.size)
        assertEquals("1", routine.exercises[0].id)
        assertEquals("Sprints en cinta", routine.exercises[0].nombre)
    }

    @Test
    fun `should update exercise when already exists`(){
        val routine = Routine(
            name = "Routine with Exercises",
            img = null,
            description = "Routine with multiple exercises",
            category = "Cardio",
            exercises = mutableListOf(sampleExercise()),
            difficulty = "Intermediate"
        )

        routine.addExercise(Exercise(
            id = "1",
            nombre = "ejercicio actualizado",
            descripcion = "Sprints a máxima velocidad",
            repeticiones = 12,
            peso = 0.0,
            musculo = "Piernas",
            series = 4,
            descansoSegundos = 30,
            equipo = "Cinta",
            instrucciones = "Corre a máxima velocidad por 30 segundos"
        ))

        assertEquals(1, routine.exercises.size)
        assertEquals("1", routine.exercises[0].id)
        assertEquals("ejercicio actualizado", routine.exercises[0].nombre)
    }

    @Test
    fun `should delete exercise when already exists`(){
        val routine = Routine(
            name = "Routine with Exercises",
            img = null,
            description = "Routine with multiple exercises",
            category = "Cardio",
            exercises = mutableListOf(sampleExercise()),
            difficulty = "Intermediate"
        )

        routine.deleteExercise("1")

        assertEquals(0, routine.exercises.size)
    }

    @Test
    fun `should throw NoSuchElementException when exercise does not exists`(){
        val routine = Routine(
            name = "Routine with Exercises",
            img = null,
            description = "Routine with multiple exercises",
            category = "Cardio",
            exercises = mutableListOf(),
            difficulty = "Intermediate"
        )

        val ex = assertThrows<NoSuchElementException> {
            routine.deleteExercise("1")
        }

        assertEquals("No se encontró el ejercicio con id: 1", ex.message)
    }

    @Test
    fun `equals should return true for same id`() {
        val routine1 = Routine(
            id = "1",
            name = "Routine 1",
            img = null,
            description = "Description 1",
            category = "Category 1",
            difficulty = "Easy",
            durationMinutes = 30,
            objective = "Objective 1",
            weeklyFrequency = 3,
            exercises = mutableListOf(sampleExercise())
        )

        val routine2 = Routine(
            id = "1",
            name = "Routine 2",
            img = null,
            description = "Description 2",
            category = "Category 2",
            difficulty = "Medium",
            durationMinutes = 45,
            objective = "Objective 2",
            weeklyFrequency = 4,
            exercises = mutableListOf(sampleExercise())
        )

        assertEquals(routine1, routine2)
    }

    @Test
    fun `equals should return false for different ids`() {
        val routine1 = Routine(
            id = "1",
            name = "Routine 1",
            img = null,
            description = "Description 1",
            category = "Category 1",
            difficulty = "Easy",
            durationMinutes = 30,
            objective = "Objective 1",
            weeklyFrequency = 3,
            exercises = mutableListOf(sampleExercise())
        )

        val routine2 = Routine(
            id = "2",
            name = "Routine 2",
            img = null,
            description = "Description 2",
            category = "Category 2",
            difficulty = "Medium",
            durationMinutes = 45,
            objective = "Objective 2",
            weeklyFrequency = 4,
            exercises = mutableListOf(sampleExercise())
        )

        assertEquals(false, routine1 == routine2)
    }

    @Test
    fun `hashCode should return same value for same id`() {
        val routine1 = Routine(
            id = "1",
            name = "Routine 1",
            img = null,
            description = "Description 1",
            category = "Category 1",
            difficulty = "Easy",
            durationMinutes = 30,
            objective = "Objective 1",
            weeklyFrequency = 3,
            exercises = mutableListOf(sampleExercise())
        )

        val routine2 = Routine(
            id = "1",
            name = "Routine 2",
            img = null,
            description = "Description 2",
            category = "Category 2",
            difficulty = "Medium",
            durationMinutes = 45,
            objective = "Objective 2",
            weeklyFrequency = 4,
            exercises = mutableListOf(sampleExercise())
        )

        assertEquals(routine1.hashCode(), routine2.hashCode())
    }

    @Test
    fun `hashCode should return different values for different ids`() {
        val routine1 = Routine(
            id = "1",
            name = "Routine 1",
            img = null,
            description = "Description 1",
            category = "Category 1",
            difficulty = "Easy",
            durationMinutes = 30,
            objective = "Objective 1",
            weeklyFrequency = 3,
            exercises = mutableListOf(sampleExercise())
        )

        val routine2 = Routine(
            id = "2",
            name = "Routine 2",
            img = null,
            description = "Description 2",
            category = "Category 2",
            difficulty = "Medium",
            durationMinutes = 45,
            objective = "Objective 2",
            weeklyFrequency = 4,
            exercises = mutableListOf(sampleExercise())
        )

        assertEquals(false, routine1.hashCode() == routine2.hashCode())
    }
}