package example.chiwoo.demo.jobs.csvtoconsole

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.step.tasklet.TaskletStep
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

@Configuration
class CsvToConsoleJobConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory
) {

    companion object {
        private const val CHUNK_SIZE = 10
    }

    @Value("classpath:data/record.csv")
    private val inputCsv: Resource? = null

//    @Bean
//    @Throws(UnexpectedInputException::class, ParseException::class)
//    fun itemReader(): CsvItemReader<ItemModel>? {
//        val reader: FlatFileItemReader<ItemModel> = FlatFileItemReader<ItemModel>()
//        val tokenizer = DelimitedLineTokenizer()
//        val tokens = arrayOf("username", "userid", "transactiondate", "amount")
//        tokenizer.setNames(*tokens)
//        inputCsv?.let { reader.setResource(it) }
//        val lineMapper: DefaultLineMapper<ItemModel> = DefaultLineMapper<ItemModel>()
//        lineMapper.setLineTokenizer(tokenizer)
//        lineMapper.setFieldSetMapper(CsvFieldSetMapper<Any?>())
//        reader.setLineMapper(lineMapper)
//        return reader
//    }
//
//    @Bean
//    fun itemProcessor(): ItemProcessor<ItemModel?, ItemModel?>? {
//        return CustomItemProcessor()
//    }
//
//    @Bean
//    @Throws(MalformedURLException::class)
//    fun itemWriter(marshaller: Marshaller?): ItemWriter<ItemModel>? {
//        val itemWriter: StaxEventItemWriter<ItemModel> = StaxEventItemWriter<ItemModel>()
//        itemWriter.setMarshaller(marshaller!!)
//        itemWriter.setRootTagName("transactionRecord")
//        itemWriter.setResource(outputXml!!)
//        return itemWriter
//    }
//
//    @Bean
//    fun marshaller(): Marshaller? {
//        val marshaller = Jaxb2Marshaller()
//        marshaller.setClassesToBeBound(*arrayOf(ItemModel::class.java))
//        return marshaller
//    }
//
//    @Bean
//    fun step1(
//        reader: ItemReaderCsv(inputCsv),
//        processor: ItemProcessor<ItemModel?, ItemModel?>?,
//        writer: ItemWriter<ItemModel?>?
//    ): Step? {
//        return steps!!["step1"].chunk<ItemModel, ItemModel>(10)
//            .reader(reader!!).processor(processor!!).writer(writer!!).build()
//    }
//
//    @Bean(name = ["csvToXmlJob"])
//    fun csvToXmlJob(@Qualifier("step1") step1: Step): Job? {
//        return jobs!!["csvToXmlJob"].start(step1).build()
//    }

    @Bean
    fun reader(): ItemReaderCsv {
        return ItemReaderCsv(inputCsv)
    }

    @Bean
    fun writer(): ItemWriterConsole {
        return ItemWriterConsole()
    }

    @Bean
    fun processor(): ItemProcessorModel {
        return ItemProcessorModel()
    }

    @Bean
    @StepScope
    fun csvConsoleStep1(): TaskletStep {
        val reader = ItemReaderCsv(inputCsv)
        val processor = ItemProcessorModel()
        val writer = ItemWriterConsole()
        return stepBuilderFactory["csvConsoleStep1"]
            .chunk<ItemModel, ItemModel>(CHUNK_SIZE)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build()
    }

    @Bean
    fun csvToConsoleJob(csvConsoleStep1: Step): Job {
        return jobBuilderFactory.get("csvToConsoleJob")
            // .validator()
            .incrementer(RunIdIncrementer())
            .flow(csvConsoleStep1)
            .end()
            .build()
    }


}