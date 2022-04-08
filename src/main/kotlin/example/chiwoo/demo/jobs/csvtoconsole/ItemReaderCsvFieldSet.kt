package example.chiwoo.demo.jobs.csvtoconsole

import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ItemReaderCsvFieldSet : FieldSetMapper<ItemModel> {

    companion object {
        val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    }

    val toLocalDateTime: (String) -> LocalDateTime = { LocalDateTime.parse(it, FORMATTER) }

    override fun mapFieldSet(fieldSet: FieldSet): ItemModel {
        return ItemModel(
            fieldSet.readString("username"), // username
            fieldSet.readInt(1), // user_id
            toLocalDateTime(fieldSet.readString(2)), // transaction_date
            fieldSet.readDouble(3) // transaction_amount
        )
    }
}