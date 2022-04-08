package example.chiwoo.demo.jobs.hellolistener

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener

class HelloJobExecutionListener: JobExecutionListener {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(javaClass)
    }

    override fun beforeJob(execution: JobExecution ) {
        log.info("beforeJob: {}", execution)
    }

    override fun afterJob(execution: JobExecution) {
        log.info("afterJob: {}", execution)
    }
}