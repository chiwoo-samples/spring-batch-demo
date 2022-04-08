package example.chiwoo.demo.jobs.hello03

import example.chiwoo.demo.jobs.JobLogger
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener

class HelloJobExecutionListener : JobExecutionListener {

    override fun beforeJob(execution: JobExecution) {
        JobLogger.log.info("beforeJob: {}", execution)
    }

    override fun afterJob(execution: JobExecution) {
        JobLogger.log.info("afterJob: {}", execution)
    }
}