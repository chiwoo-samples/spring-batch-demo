package example.chiwoo.demo.jobs.hellolistener

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.*
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HelloListenerJobConfig(
    val jobBuilderFactory: JobBuilderFactory, val stepBuilderFactory: StepBuilderFactory,
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(javaClass)
    }

    @Bean
    fun helloJobExecutionListener(): JobExecutionListener {
        return HelloJobExecutionListener()
    }

    @Bean
    fun helloStepExecutionListener(): StepExecutionListener {
        return HelloStepExecutionListener()
    }

    @Bean
    fun helloChunkListener(): ChunkListener {
        return HelloChunkListener()
    }

    @Bean
    fun helloListenerStep(): Step {
        return stepBuilderFactory.get("helloListenerStep")
            .chunk<String, String>(1)
            .reader { "hello, world" }
            .processor(ItemProcessor<String, String> { value -> value.uppercase() })
            .writer { value -> log.info("{}", value) }
            .listener(helloChunkListener())
            .listener(helloStepExecutionListener())
            .build()
    }

    @Bean
    fun helloListenerJob(helloListenerStep: Step): Job {
        return jobBuilderFactory.get("helloListenerJob")
            .listener(helloJobExecutionListener())
            .incrementer(RunIdIncrementer())
            .flow(helloListenerStep)
            .end()
            .build()
    }
}