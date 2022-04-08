package example.chiwoo.demo.jobs.csvtoconsole

import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.core.io.Resource

class ItemReaderCsv(inputCsv: Resource?) : FlatFileItemReader<ItemModel>() {

    companion object {
        val COLUMNS = arrayOf("username", "userid", "transactiondate", "amount")
        const val DELIMITER = ","
    }

    // private val tokenizer: DelimitedLineTokenizer = DelimitedLineTokenizer()

    init {
        super.setResource(inputCsv!!)
        super.setLinesToSkip(1)
        super.setLineMapper(object : DefaultLineMapper<ItemModel>() {
            init {
                setLineTokenizer(object : DelimitedLineTokenizer() {
                    init {
                        setNames(*COLUMNS)
                        setDelimiter(DELIMITER)
                    }
                })
                setFieldSetMapper(ItemReaderCsvFieldSet())
            }
        })
    }

//    private val tokens = arrayOf("username", "userid", "transactiondate", "amount")
//    private val tokenizer: DelimitedLineTokenizer = DelimitedLineTokenizer()
//
//    @Throws(UnexpectedInputException::class, ParseException::class)
//    override fun read(): ItemModel? {
//        super.setResource(inputCsv)
//
//        tokenizer.setNames(*tokens)
//        val lineMapper: DefaultLineMapper<ItemModel> = DefaultLineMapper<ItemModel>()
//        lineMapper.setLineTokenizer(tokenizer)
//        lineMapper.setFieldSetMapper(CsvFieldSetMapper())
//        setLineMapper(lineMapper)
//    }
}