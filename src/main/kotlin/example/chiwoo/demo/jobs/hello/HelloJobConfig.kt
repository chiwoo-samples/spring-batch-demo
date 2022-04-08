package example.chiwoo.demo.jobs.hello

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HelloJobConfig(
    val jobBuilderFactory: JobBuilderFactory, val stepBuilderFactory: StepBuilderFactory,
) {

    fun helloWorldItemReader(): ItemReader<String> {
        return object : ItemReader<String> {

            private val STRING_VALUES = listOf(
                "We", "are", "processing",
                "your", "order", "and",
                "you", "will", "a",
                "confirmation", "receive", "soon."
            )

            var index: Int = -1

            override fun read(): String? {
                this.index += 1
                if (this.index >= STRING_VALUES.size) {
                    return null
                }
                return STRING_VALUES[index]
            }

        }

    }

    @Bean
    @StepScope
    fun step1(): Step {
        return stepBuilderFactory
            .get("step1")
            .chunk<String, String>(3)
            .reader(helloWorldItemReader())
            .processor(ItemProcessor<String, String> { value -> value.uppercase() })
            .writer(ItemWriter<String> { value -> println(value) })
            .build()
    }

    @Bean
    fun helloJob(step1: Step): Job {
        return jobBuilderFactory.get("helloJob")
            .incrementer(RunIdIncrementer())
            .flow(step1).end()
            .build()
    }

}