package example.chiwoo.demo

import example.chiwoo.demo.utils.DateUtils
import example.chiwoo.demo.utils.RandomUtils
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

class DataGenerator {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(javaClass)

        const val NL: Char = '\n'
        const val CM: String = ", "
    }

    @Test
    fun generate() {

        val file = File("src/main/resources/data/input2.csv")
        file.createNewFile()
        log.info("file.absoluteFile: {}", file.absoluteFile)

        file.bufferedWriter().use { out ->
            val header = StringBuilder()
                .append("username").append(CM)
                .append("user_id").append(CM)
                .append("transaction_date").append(CM)
                .append("transaction_amount").toString()

            out.write(header)
            for (i in 1..100) {
                out.write(
                    StringBuilder()
                        .append(NL)
                        .append(RandomUtils.nextWord(10)).append(CM)
                        .append(RandomUtils.nextInt(10000, 999999)).append(CM)
                        .append(RandomUtils.nextLocalDtmFromYear(2017).format(DateUtils.FORMATTER)).append(CM)
                        .append(RandomUtils.nextLong(10, 9999999))
                        .toString()
                )
            }
        }
    }

}