package example.chiwoo.demo.jobs.csvtoxml

import example.chiwoo.demo.NoArg
import example.chiwoo.demo.utils.LocalDateTimeAdapter
import java.time.LocalDateTime
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlType
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@NoArg
@XmlRootElement(name = "UserTransaction")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "userTx", propOrder = ["username", "userId", "transactionDate", "amount"])
data class ItemModel(
    val username: String?,
    val userId: Int?,
    @field:XmlJavaTypeAdapter(
        value = LocalDateTimeAdapter::class,
        type = LocalDateTime::class
    ) val transactionDate: LocalDateTime? = null,
    val amount: Double?
)