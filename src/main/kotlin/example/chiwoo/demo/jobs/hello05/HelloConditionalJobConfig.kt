package example.chiwoo.demo.jobs.hello05

import example.chiwoo.demo.jobs.JobLogger
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HelloConditionalJobConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    @JobScope
    fun conditionalStep1(@Value("#{jobParameters['answer']}") answer: String?): Step {
        return stepBuilderFactory
            .get("conditionalStep1")
            .tasklet { contribution, chunkContext ->
                JobLogger.log.info("conditionalStep1-task")
                var value: Int? = null
                if (answer == null) {
                    value =
                        (chunkContext.stepContext.jobParameters.getOrDefault("answer", "1") as String).toInt()
                } else {
                    value = answer.toInt()
                }

                JobLogger.log.info("answer: {}", value)
                if ((value!! % 2) == 0) {
                    contribution.exitStatus = ExitStatus.FAILED
                }
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    @JobScope
    fun conditionalStep2(): Step {
        return stepBuilderFactory
            .get("conditionalStep2")
            .tasklet { contribution, chunkContext ->
                JobLogger.log.info("conditionalStep2-task")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    @JobScope
    fun conditionalStep3(): Step {
        return stepBuilderFactory
            .get("conditionalStep3")
            .tasklet { contribution, chunkContext ->
                JobLogger.log.info("conditionalStep3-task")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    @JobScope
    fun conditionalStep4(): Step {
        return stepBuilderFactory
            .get("conditionalStep4")
            .tasklet { contribution, chunkContext ->
                JobLogger.log.info("conditionalStep4-task")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun helloConditionalJob(
        conditionalStep1: Step,
        conditionalStep2: Step,
        conditionalStep3: Step,
        conditionalStep4: Step,
    ): Job {
        return jobBuilderFactory.get("helloConditionalJob")
            .incrementer(RunIdIncrementer())
            .start(conditionalStep1)
            .on("*")
            .to(conditionalStep2)
            .next(conditionalStep3)
            .on("*")
            .end()
            .from(conditionalStep1)
            .on(ExitStatus.FAILED.exitCode)
            .to(conditionalStep4)
            .on("*")
            .end()
            .end()
            .build()
    }
}