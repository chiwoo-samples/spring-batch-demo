package example.chiwoo.demo.jobs

import example.chiwoo.demo.utils.DateUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.beans.factory.annotation.Autowired

class Hello04SequentialJobTest : AbstractBatchTest() {

    @Autowired
    private lateinit var helloSequentialJob: Job

    /**
     * @see example.chiwoo.demo.jobs.hello04.HelloSequentialJobConfig.helloSequentialJob
     */
    @Test
    @Throws(Exception::class)
    fun testBatch() {
        log.info("job: {}", helloSequentialJob)
        val jobParameters = JobParametersBuilder().addString("now", DateUtils.currentDtmToString).toJobParameters()
        val jobExecution = launchJob(helloSequentialJob, jobParameters)
        log.info("jobExecution.status: {}", jobExecution.status)
        assertEquals(BatchStatus.COMPLETED, jobExecution.status)
    }
}