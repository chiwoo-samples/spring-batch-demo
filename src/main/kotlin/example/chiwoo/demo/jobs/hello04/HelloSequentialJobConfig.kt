package example.chiwoo.demo.jobs.hello04

import example.chiwoo.demo.jobs.JobLogger
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HelloSequentialJobConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    @JobScope
    fun hello04Step01(): Step {
        return stepBuilderFactory
            .get("hello04Step01")
            .tasklet { contribution, chunkContext ->
                JobLogger.log.info("firstStep-task")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    @JobScope
    fun hello04Step02(): Step {
        return stepBuilderFactory
            .get("hello04Step02")
            .tasklet { contribution, chunkContext ->
                JobLogger.log.info("secondStep-task")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    @JobScope
    fun hello04Step03(): Step {
        return stepBuilderFactory
            .get("hello04Step03")
            .tasklet { contribution, chunkContext ->
                JobLogger.log.info("thirdStep-task")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun helloSequentialJob(
        hello04Step01: Step,
        hello04Step02: Step,
        hello04Step03: Step,
    ): Job {
        return jobBuilderFactory.get("helloSequentialJob")
            .incrementer(RunIdIncrementer())
            .start(hello04Step01)
            .next(hello04Step02)
            .next(hello04Step03)
            .build()
    }
}