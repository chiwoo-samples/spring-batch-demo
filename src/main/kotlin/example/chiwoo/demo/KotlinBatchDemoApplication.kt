package example.chiwoo.demo

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableBatchProcessing
@SpringBootApplication
class KotlinBatchDemoApplication

fun main(args: Array<String>) {
	runApplication<KotlinBatchDemoApplication>(*args)
}
