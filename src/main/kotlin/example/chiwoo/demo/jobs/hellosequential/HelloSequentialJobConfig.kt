package example.chiwoo.demo.jobs.hellosequential

import org.slf4j.Logger
import org.slf4j.LoggerFactory
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
    val stepBuilderFactory: StepBuilderFactory
) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(javaClass)
    }

    @Bean
    @JobScope
    fun firstStep(): Step {
        return stepBuilderFactory
            .get("firstStep")
            .tasklet { contribution, chunkContext ->
                log.info("firstStep-task")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    @JobScope
    fun secondStep(): Step {
        return stepBuilderFactory
            .get("secondStep")
            .tasklet { contribution, chunkContext ->
                log.info("secondStep-task")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    @JobScope
    fun thirdStep(): Step {
        return stepBuilderFactory
            .get("thirdStep")
            .tasklet { contribution, chunkContext ->
                log.info("thirdStep-task")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun helloSequentialJob(
        firstStep: Step,
        secondStep: Step,
        thirdStep: Step
    ): Job {
        return jobBuilderFactory.get("helloSequentialJob")
            .incrementer(RunIdIncrementer())
            .start(firstStep)
            .next(secondStep)
            .next(thirdStep)
            .build()
    }
}