package example.chiwoo.demo.jobs.hello03

import example.chiwoo.demo.jobs.JobLogger
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener

class HelloStepExecutionListener : StepExecutionListener {

    override fun beforeStep(execution: StepExecution) {
        JobLogger.log.info("beforeStep: {}", execution)
    }

    override fun afterStep(execution: StepExecution): ExitStatus? {
        JobLogger.log.info("afterStep: {}", execution)
        return ExitStatus.COMPLETED
    }

}