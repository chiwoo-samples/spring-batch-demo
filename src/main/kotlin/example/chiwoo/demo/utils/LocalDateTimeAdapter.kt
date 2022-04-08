package example.chiwoo.demo.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.time.ZoneId
import javax.xml.bind.annotation.adapters.XmlAdapter

class LocalDateTimeAdapter : XmlAdapter<String?, LocalDateTime?>() {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(javaClass)
    }

    @Throws(Exception::class)
    override fun unmarshal(value: String?): LocalDateTime? {
        return try {
            if (value == null) {
                return null
            }
            DateUtils.toLocalDateTime(value)
        } catch (e: Exception) {
            log.error("Deserializer error [$value].", e)
            throw e
        }
    }

    @Throws(Exception::class)
    override fun marshal(value: LocalDateTime?): String? {
        return try {
            if (value == null) {
                return null
            }
            DateUtils.localDtmToString(value)
        } catch (e: Exception) {
            log.error("Serializer error [$value].", e)
            throw e
        }
    }

}