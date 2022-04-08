package example.chiwoo.demo.jobs.hello03

import example.chiwoo.demo.jobs.JobLogger
import org.springframework.batch.core.*
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HelloListenerJobConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
) {

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
    fun hello03Step01(): Step {

        //
        //
        val iterator = """
            A "Hello, world!" program is generally a computer program that outputs or displays the message "Hello, world!"
            This program is very simple to write in many programming languages, and is often used to illustrate a language's basic syntax. 
            "Hello, world!" programs are often the first a student learns to write in a given language,
            and they can also be used as a sanity test to ensure computer software intended to compile or run source code is correctly installed, 
            and that its operator understands how to use it. (from WIKI)
        """.trimIndent()
            .split(" ").iterator()

        return stepBuilderFactory.get("hello03Step01")
            .chunk<String, String>(5)
            .reader { if (iterator.hasNext()) iterator.next() else null }
            .processor(ItemProcessor<String, String> { value -> value.trim().uppercase() })
            .writer { value -> JobLogger.log.info("{}", value) }
            .listener(helloChunkListener())
            .listener(helloStepExecutionListener())
            .build()
    }

    @Bean
    fun hello03Step02(): Step {
        return stepBuilderFactory.get("hello03Step02")
            .tasklet { contribution, chunkContext ->
                JobLogger.log.info("contribution: {}", contribution)
                JobLogger.log.info("chunkContext: {}", chunkContext)
                JobLogger.log.info("Hello. Symplesims!")
                RepeatStatus.FINISHED
            }
            .listener(helloChunkListener())
            .listener(helloStepExecutionListener())
            .build()
    }

    @Bean
    fun helloListenerJob(hello03Step01: Step, hello03Step02: Step): Job {
        return jobBuilderFactory.get("helloListenerJob")
            .listener(helloJobExecutionListener())
            .incrementer(RunIdIncrementer())
            .flow(hello03Step01)
            .next(hello03Step02)
            .end()
            .build()
    }
}