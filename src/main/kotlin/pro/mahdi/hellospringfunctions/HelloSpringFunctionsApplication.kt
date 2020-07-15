package pro.mahdi.hellospringfunctions

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import reactor.core.publisher.Flux

@SpringBootApplication
class HelloSpringFunctionsApplication {
    data class Message(val message: String)

    @Bean
    fun hello(): () -> String = {
        "Hello"
    }

    @Bean
    fun uppercase(): (Flux<Message>) -> Flux<Message> = {
        it.map { Message(it.message.toUpperCase()) }
    }

}

fun main(args: Array<String>) {
    runApplication<HelloSpringFunctionsApplication>(*args)
}
