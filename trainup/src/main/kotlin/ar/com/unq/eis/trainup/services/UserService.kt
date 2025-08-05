package ar.com.unq.eis.trainup.services

import ar.com.unq.eis.trainup.model.User

interface UserService {

    fun createUser(user: User): User

    fun getUsers(): List<User>

    fun getUserByUsername(username: String): User

    fun getUserByID(id:String): User

    fun updateUser(updatedUser: User): User

    fun deleteUser(id: String)

    fun logIn(username: String, password: String): User

    fun completeRoutine(userID:String, routineID:String)
     fun toggleRoutineFollow(userID: String, routineID: String):User

     fun isFollowing(userID: String, routineID: String):Boolean

     fun toggleExerciseCompletion(userId: String, routineID: String, exerciseID: String)

     fun addFavRoutine(userID: String, routineID: String): User
}
