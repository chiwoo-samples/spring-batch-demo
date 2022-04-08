package example.chiwoo.demo.jobs.hellocondition

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * 정상인경우 Step1 > Step2 > Step3
 * Step1 이 실패할 경우 Step4
 */
@Configuration
class HelloConditionalJobConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(javaClass)
    }

    @Bean
    @JobScope
    fun conditionalStep1(@Value("#{jobParameters['processorNo']}") prcNo: String?): Step {
        return stepBuilderFactory
            .get("conditionalStep1")
            .tasklet { contribution, chunkContext ->
                log.info("conditionalStep1-task")

                log.info("prcNo: {}", prcNo)
                var processorNo: Int? = null
                if (prcNo == null) {
                    processorNo =
                        (chunkContext.stepContext.jobParameters.getOrDefault("processorNo", "1") as String).toInt()
                } else {
                    processorNo = prcNo.toInt()
                }

                log.info("processorNo: {}", processorNo)
                if ((processorNo!! % 2) == 0) {
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
                log.info("conditionalStep2-task")
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
                log.info("conditionalStep3-task")
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
                log.info("conditionalStep4-task")
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