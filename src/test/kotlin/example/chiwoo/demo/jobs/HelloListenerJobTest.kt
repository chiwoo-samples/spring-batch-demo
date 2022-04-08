package example.chiwoo.demo.jobs

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.beans.factory.annotation.Autowired

class HelloListenerJobTest : AbstractBatchTest() {

    @Autowired
    private lateinit var helloListenerJob: Job

    // @Rollback
    @Test
    @Throws(Exception::class)
    fun testBatch() {
        log.info("job: {}", helloListenerJob)
        val jobParameters = JobParametersBuilder().addString("today", "2022-01-04").toJobParameters()
        val jobExecution = launchJob(helloListenerJob, jobParameters)
        log.info("jobExecution.status: {}", jobExecution.status)
        assertEquals(BatchStatus.COMPLETED, jobExecution.status)
    }
}