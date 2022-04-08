package example.chiwoo.demo.jobs.csvtoxml

import example.chiwoo.demo.utils.DateUtils
import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet

class ItemReaderCsvFieldSet : FieldSetMapper<ItemModel> {

    override fun mapFieldSet(fieldSet: FieldSet): ItemModel {
        return ItemModel(
            fieldSet.readString("username"), // username
            fieldSet.readInt(1), // user_id
            DateUtils.toLocalDateTime(fieldSet.readString(2)),  // transaction_date
            fieldSet.readDouble(3) // transaction_amount
        )
    }
}