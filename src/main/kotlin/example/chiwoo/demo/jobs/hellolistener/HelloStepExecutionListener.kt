package example.chiwoo.demo.jobs.hellolistener

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.*

class HelloStepExecutionListener : StepExecutionListener {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(javaClass)
    }

    override fun beforeStep(execution: StepExecution) {
        log.info("beforeStep: {}", execution)
    }

    override fun afterStep(execution: StepExecution): ExitStatus? {
        log.info("afterStep: {}", execution)
        return ExitStatus.COMPLETED
    }

}