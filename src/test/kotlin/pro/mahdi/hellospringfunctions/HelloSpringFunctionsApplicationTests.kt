package pro.mahdi.hellospringfunctions

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@RunWith(SpringRunner::class)
@FunctionalSpringBootTest
@AutoConfigureWebTestClient
class HelloSpringFunctionsApplicationTests {

    @Autowired
    lateinit var client: WebTestClient

    @Test
    fun contextLoads() {
    }

    @Test
    fun helloFunctionReturnsHello() {
        val response = client.get()
                .uri("/hello")
                .exchange()

        response.expectStatus().isOk
        response.expectBody<String>().isEqualTo("Hello")
    }

    @Test
    fun uppercaseFunctionReturnsUppercasedMessageStream() {
        val response = client.post()
                .uri("/uppercase")
                .bodyValue("Hello")
                .exchange()
        response.expectStatus().isOk
        response.expectBody<Message>().isEqualTo(Message("HELLO"))
    }
}
