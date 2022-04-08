package example.chiwoo.demo.jobs.csvtoxml

import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.core.io.Resource

class ItemReaderCsv(inputCsv: Resource?) : FlatFileItemReader<ItemModel>() {

    companion object {
        val COLUMNS = arrayOf("username", "userid", "transactiondate", "amount")
        const val DELIMITER = ","
    }

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

}