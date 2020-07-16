package pro.mahdi.hellospringfunctions

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.function.context.FunctionRegistration
import org.springframework.cloud.function.context.FunctionType
import org.springframework.cloud.function.context.FunctionalSpringApplication
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.registerBean
import java.util.function.Function
import java.util.function.Supplier

@SpringBootApplication
class HelloSpringFunctionsApplication : ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(context: GenericApplicationContext) {
        val hello = Supplier<String> { "Hello" }
        val uppercase = Function<Message, Message> { Message(it.message.toUpperCase()) }

        context.apply {
            registerBean("hello") {
                FunctionRegistration(hello, "hello").type(FunctionType.supplier(String::class.java))
            }
            registerBean("uppercase") {
                FunctionRegistration(uppercase, "uppercase").type(FunctionType.from(Message::class.java).to(Message::class.java))
            }
        }
    }
}

fun main(args: Array<String>) {
    FunctionalSpringApplication.run(HelloSpringFunctionsApplication::class.java, *args)
}

data class Message(val message: String)
