package example.chiwoo.demo.jobs

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.beans.factory.annotation.Autowired

class HelloConditionalJobTest : AbstractBatchTest() {


    @Autowired
    private lateinit var helloConditionalJob: Job

    /**
     * @see example.chiwoo.demo.jobs.hellocondition.HelloConditionalJobConfig
     * processorNo 가 홀수이면 step 1,2,3 을 순차적으로 진행, 짝수 이면 step 1 실패, step 4 를 실행
     */
    // @Rollback
    @Test
    @Throws(Exception::class)
    fun testBatch() {
        log.info("job: {}", helloConditionalJob)
        val jobParameters = JobParametersBuilder()
            .addString("today", "2022-01-04")
            .addString("processorNo", "2")
            .toJobParameters()
        val jobExecution = launchJob(helloConditionalJob, jobParameters)
        log.info("jobExecution.status: {}", jobExecution.status)
        assertEquals(BatchStatus.COMPLETED, jobExecution.status)
    }
}