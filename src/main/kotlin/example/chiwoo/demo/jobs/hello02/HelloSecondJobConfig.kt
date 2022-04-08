package example.chiwoo.demo.jobs.hello02

import example.chiwoo.demo.jobs.JobLogger
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HelloSecondJobConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
) {

    fun stringArrayItemReader(): ItemReader<String> {
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
    fun helloSecondStep(): Step {
        return stepBuilderFactory
            .get("helloSecondStep")
            .chunk<String, String>(3)
            .reader(stringArrayItemReader())
            .processor(ItemProcessor<String, String> { value -> value.uppercase() })
            .writer(ItemWriter<String> { v -> JobLogger.log.info("{}", v) })
            .build()
    }

    @Bean
    fun helloSecondJob(helloSecondStep: Step): Job {
        return jobBuilderFactory.get("helloSecondJob")
            .incrementer(RunIdIncrementer())
            .flow(helloSecondStep).end()
            .build()
    }

}