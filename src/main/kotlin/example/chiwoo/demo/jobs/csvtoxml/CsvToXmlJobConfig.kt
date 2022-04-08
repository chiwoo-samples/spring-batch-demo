package example.chiwoo.demo.jobs.csvtoxml

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.step.tasklet.TaskletStep
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.oxm.jaxb.Jaxb2Marshaller

@Configuration
class CsvToXmlJobConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val jaxb2Marshaller: Jaxb2Marshaller
) {

    companion object {
        private const val CHUNK_SIZE = 10
    }

    @Value("classpath:data/record.csv")
    private val inputCsv: Resource? = null

    @Value("file:target/output.xml")
    private val outputXml: Resource? = null

    @Bean
    fun csvToXmlStep(): TaskletStep {
        return stepBuilderFactory
            .get("csvToXmlStep")
            .chunk<ItemModel, ItemModel>(CHUNK_SIZE)
            .reader(ItemReaderCsv(inputCsv))
            .processor(ItemProcessorModel())
            .writer(ItemWriterXml(jaxb2Marshaller, outputXml)).build()
    }

    @Bean
    fun csvToXmlJob(csvToXmlStep: Step): Job {
        return jobBuilderFactory
            .get("csvToXmlStep")
            .incrementer(RunIdIncrementer())
            .flow(csvToXmlStep)
            .end().build()
    }

}