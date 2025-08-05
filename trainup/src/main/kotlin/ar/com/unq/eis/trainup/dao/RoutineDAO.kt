package ar.com.unq.eis.trainup.dao

import ar.com.unq.eis.trainup.model.Routine
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RoutineDAO : MongoRepository<Routine, String> {

    @Query(
        "{ 'category' : ?0 }"
    )
    fun findByCategory(category: String): List<Routine>

    fun findByNameContainingIgnoreCase(name: String): List<Routine>

    fun findByNameContainingIgnoreCaseAndDifficulty(name: String, difficulty:String?): List<Routine>
}