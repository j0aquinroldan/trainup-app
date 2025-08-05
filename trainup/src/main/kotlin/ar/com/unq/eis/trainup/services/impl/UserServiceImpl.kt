package ar.com.unq.eis.trainup.services.impl

import ar.com.unq.eis.trainup.controller.Exceptions.UsuarioDuplicadoException
import ar.com.unq.eis.trainup.dao.RoutineDAO
import ar.com.unq.eis.trainup.dao.UserDAO
import ar.com.unq.eis.trainup.model.Routine
import ar.com.unq.eis.trainup.model.User
import ar.com.unq.eis.trainup.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    @Autowired private val userDAO: UserDAO,
    @Autowired private val routineDAO: RoutineDAO
) : UserService {

    override fun createUser(user: User): User {
        return try {
            userDAO.save(user)
        } catch (e: DuplicateKeyException) {
            throw UsuarioDuplicadoException(user.username)
        }
    }

    override fun getUsers(): List<User> {
        return userDAO.findAll()
    }

    override fun getUserByUsername(username: String): User {
        return userDAO.findByUsername(username).orElseThrow {
            NoSuchElementException("No existe usuario ${username}")
        }
    }

    override fun getUserByID(id: String): User {
        return userDAO.findById(id).orElseThrow {
            NoSuchElementException("No existe usuario con id ${id}")
        }
    }

    override fun updateUser(updatedUser: User): User {

        val id = updatedUser.id ?: throw IllegalArgumentException("id no puede ser null")
        val existingUser =
            userDAO.findById(id).orElseThrow { NoSuchElementException("No se encontró el usuario con id: $id") }

        existingUser.name = updatedUser.name
        existingUser.age = updatedUser.age
        existingUser.birthDate = updatedUser.birthDate
        existingUser.phone = updatedUser.phone
        existingUser.genre = updatedUser.genre
        existingUser.height = updatedUser.height
        existingUser.weight = updatedUser.weight
        existingUser.objective = updatedUser.objective
        existingUser.username = updatedUser.username

        return userDAO.save(existingUser)
    }

    override fun deleteUser(id: String) {
        if (userDAO.existsById(id)) {
            userDAO.deleteById(id)
        } else {
            throw NoSuchElementException("No existe usuario con id ${id}")
        }
    }

    override fun logIn(username: String, password: String): User {
        return userDAO.findByUsernameAndPassword(username, password)
            .orElseThrow { NoSuchElementException("usuario no encontrado") }
    }

    override fun completeRoutine(userID: String, routineID: String) {
        val user = this.getUserByID(userID)

        user.completeRoutine(routineID)

        userDAO.save(user)
    }

    private fun getRutinaByID(rutinaID: String): Routine {
        val routine =
            this.routineDAO.findById(rutinaID).orElseThrow {
                NoSuchElementException("No existe rutina con id ${rutinaID}")
            }
        return routine
    }

    override fun toggleRoutineFollow(userID: String, routineID: String): User {
        val user = this.getUserByID(userID)
        val routine = this.getRutinaByID(routineID)

        user.toggleRoutineFollow(routine)

        return userDAO.save(user)
    }

    override fun isFollowing(userID: String, routineID: String): Boolean {
        val user = this.getUserByID(userID)

        return user.isFollowing(routineID)

    }

    override fun toggleExerciseCompletion(userId: String, routineID: String, exerciseID: String) {
        val user = this.getUserByID(userId)
        user.toggleExerciseCompletion(routineID, exerciseID)
        userDAO.save(user)
    }

    override fun addFavRoutine(userID: String, routineID: String): User {
        val user = this.getUserByID(userID)
        val routine = this.getRutinaByID(routineID)

        user.addOrRemoveFavRoutine(routine)

        return userDAO.save(user)
    }
}