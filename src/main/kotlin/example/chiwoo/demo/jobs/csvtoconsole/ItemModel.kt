package example.chiwoo.demo.jobs.csvtoconsole

import example.chiwoo.demo.NoArg
import example.chiwoo.demo.utils.LocalDateTimeAdapter
import java.time.LocalDateTime
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlType
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@NoArg
@XmlRootElement
@XmlType(namespace = "modelConsole")
@XmlAccessorType(XmlAccessType.FIELD)
data class ItemModel(
    val username: String,
    val userId: Int,
    // @get:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy hh:mm:ss")
    @field:XmlJavaTypeAdapter(value = LocalDateTimeAdapter::class, type = LocalDateTime::class)
    val transactionDate: LocalDateTime? = null,
    val amount: Double
) {
}