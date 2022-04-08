package example.chiwoo.demo.jobs

import example.chiwoo.demo.utils.DateUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.beans.factory.annotation.Autowired

class Hello01JobTest : AbstractBatchTest() {

    @Autowired
    private lateinit var helloFirstJob: Job

    /**
     * @see example.chiwoo.demo.jobs.hello01.HelloFirstJobConfig.helloFirstJob
     */
    @Test
    @Throws(Exception::class)
    fun testBatch() {
        log.info("helloFirstJob: {}", helloFirstJob)
        val jobParameters = JobParametersBuilder().addString("now", DateUtils.currentDtmToString).toJobParameters()
        val jobExecution = launchJob(helloFirstJob, jobParameters)
        log.info("jobExecution.status: {}", jobExecution.status)
        assertEquals(BatchStatus.COMPLETED, jobExecution.status)
    }
}