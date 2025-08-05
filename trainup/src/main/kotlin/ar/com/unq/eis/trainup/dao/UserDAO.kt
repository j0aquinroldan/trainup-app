package ar.com.unq.eis.trainup.dao

import ar.com.unq.eis.trainup.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserDAO: MongoRepository<User, String> {

    fun findByUsernameAndPassword(username:String, password:String): Optional<User>
    fun findByUsername(username: String): Optional<User>
}
