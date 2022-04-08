package example.chiwoo.demo.utils

import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime

class DateUtilsTest {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(javaClass)
    }

    @Test
    fun test_localDtmToString() {
        log.info("DateUtils.localDtmToString: {}", DateUtils.localDtmToString(LocalDateTime.now()))
    }
}