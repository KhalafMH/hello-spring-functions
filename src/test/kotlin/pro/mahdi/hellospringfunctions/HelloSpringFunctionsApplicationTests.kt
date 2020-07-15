package pro.mahdi.hellospringfunctions

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.body
import org.springframework.test.web.reactive.server.expectBody
import pro.mahdi.hellospringfunctions.HelloSpringFunctionsApplication.Message
import reactor.kotlin.test.test

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
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
        val inputFlow = flowOf(Message("Hello"), Message("Hi"))
                .onEach { println("sending $it") }
        val response = client.post()
                .uri("/uppercase")
                .body(inputFlow)
                .exchange()

        response.expectStatus().isOk
        response.returnResult(Message::class.java).responseBody
                .test()
                .expectNext(Message("HELLO"))
                .expectNext(Message("HI"))
                .expectComplete()
                .verify()
    }
}
