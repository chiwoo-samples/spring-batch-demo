package example.chiwoo.demo.jobs

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class, MockitoExtension::class)
@SpringBootTest
class AbstractBatchTest {

    companion object {
        val log: Logger = LoggerFactory.getLogger(javaClass)
    }

    @Mock
    lateinit var jobLauncherTestUtils: JobLauncherTestUtils

    @Autowired
    private lateinit var jobRepository: JobRepository

    @Autowired
    private lateinit var jobLauncher: JobLauncher


    @BeforeEach
    fun init() {
        jobLauncherTestUtils = JobLauncherTestUtils()
        jobLauncherTestUtils.jobLauncher = jobLauncher
    }

    fun launchJob(job: Job, jobParameters: JobParameters) = run {
        JobLauncherTestUtils().let {
            it.job = job
            it.jobLauncher = this.jobLauncher
            it.jobRepository = this.jobRepository
            it.launchJob(jobParameters)
        }
    }

}