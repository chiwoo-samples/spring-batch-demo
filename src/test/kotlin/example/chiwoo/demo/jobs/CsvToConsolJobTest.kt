package example.chiwoo.demo.jobs

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.beans.factory.annotation.Autowired

class CsvToConsolJobTest : AbstractBatchTest() {
    @Autowired
    private lateinit var csvToConsoleJob: Job

    // @Rollback
    @Test
    @Throws(Exception::class)
    fun test_csvToConsoleJob() {
        log.info("csvToConsoleJob: {}", csvToConsoleJob)
        val jobParameters = JobParametersBuilder().addString("today", "2022-01-04").toJobParameters()
        val jobExecution = launchJob(csvToConsoleJob, jobParameters)
        log.info("jobExecution.status: {}", jobExecution.status)
        assertEquals(BatchStatus.COMPLETED, jobExecution.status)
    }
}