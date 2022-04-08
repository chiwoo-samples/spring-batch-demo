package example.chiwoo.demo.jobs.hello01

import example.chiwoo.demo.jobs.JobLogger
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HelloFirstJobConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    fun helloFirstStep(): Step {
        return stepBuilderFactory
            .get("helloFirstStep")
            .tasklet { contribution, chunkContext ->
                JobLogger.log.info("Hello World!")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun helloFirstJob(helloFirstStep: Step): Job {
        JobLogger.log.info("helloFirstStep: {}", helloFirstStep)
        return jobBuilderFactory.get("helloFirstJob")
            .incrementer(RunIdIncrementer())
            .flow(helloFirstStep).end()
            .build()
    }

}