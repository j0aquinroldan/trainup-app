package ar.com.unq.eis.trainup.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AppConfiguration {

    @Bean
    fun mongoTemplate(): MongoTemplate {
        val mongoUri = "mongodb+srv://root:root@cluster0.u8rkuaa.mongodb.net/trainup"
        return MongoTemplate(SimpleMongoClientDatabaseFactory(mongoUri))
    }

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("*")
                    .allowedHeaders("*")
            }
        }
    }
}
