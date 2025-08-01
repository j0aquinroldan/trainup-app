package ar.com.unq.eis.trainup.dao

import ar.com.unq.eis.trainup.model.Usuario
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioDAO: MongoRepository<Usuario, String> {

    fun findByUsernameAndPassword(username:String, password:String): Optional<Usuario>
    fun findByUsername(username: String): Optional<Usuario>
}
