package example.chiwoo.demo

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.batch.core.configuration.JobRegistry
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KotlinBatchDemoApplicationTests {

    companion object {
        private val log = LoggerFactory.getLogger(javaClass)
    }

    @Autowired
    private lateinit var jobRepository: JobRepository

    @Autowired
    private lateinit var jobLauncher: JobLauncher

    @Autowired
    private lateinit var jobRegistry: JobRegistry

    @Autowired
    private lateinit var jobExplorer: JobExplorer

    @Test
    fun contextLoads() {
        log.info("jobRepository: {}", jobRepository)
        log.info("jobLauncher: {}", jobLauncher)
        log.info("jobRegistry: {}", jobRegistry)
        log.info("jobExplorer: {}", jobExplorer)
        log.info("contextLoads: {}", jobRepository)
    }

}
