package example.chiwoo.demo.jobs

import example.chiwoo.demo.jobs.csvtoxml.ItemModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.oxm.jaxb.Jaxb2Marshaller
import java.io.File
import java.time.LocalDateTime
import javax.xml.bind.Marshaller

class CsvToXmlJobTest : AbstractBatchTest() {

    @Autowired
    private lateinit var csvToXmlJob: Job

    @Autowired
    private lateinit var jaxbMarshaller: Jaxb2Marshaller


    @Test
    @Throws(Exception::class)
    fun test_jaxbMarshaller() {
        log.info("jaxbMarshaller: {}", jaxbMarshaller)
        log.info(
            "JAXB_FORMATTED_OUTPUT: {}",
            jaxbMarshaller.createMarshaller().getProperty(Marshaller.JAXB_FORMATTED_OUTPUT)
        )
        val item = ItemModel("symple", 1111, LocalDateTime.now(), 10000.0)
        jaxbMarshaller.createMarshaller().marshal(item, System.out)
        jaxbMarshaller.createMarshaller().marshal(item, File("./target/item.xml"))
    }

    // @Rollback
    @Test
    @Throws(Exception::class)
    fun testBatchJob() {
        log.info("job: {}", csvToXmlJob)
        val jobParameters =
            JobParametersBuilder().addString("triggerUUID", System.currentTimeMillis().toString()).toJobParameters()
        val jobExecution = launchJob(csvToXmlJob, jobParameters)
        log.info("jobExecution.status: {}", jobExecution.status)
        assertEquals(BatchStatus.COMPLETED, jobExecution.status)
    }
}