package example.chiwoo.demo.utils

import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class RandomUtilsTest {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(javaClass)
    }

    @Test
    fun testRandomInt() {
        log.info("nextInt(10): {}", RandomUtils.nextInt(10))
        log.info("nextInt(10): {}", RandomUtils.nextInt(10))
        log.info("nextInt(10): {}", RandomUtils.nextInt(10))
        log.info("nextInt(100, 110): {}", RandomUtils.nextInt(100, 110))
        log.info("nextInt(100, 110): {}", RandomUtils.nextInt(100, 110))
        log.info("nextInt(100, 110): {}", RandomUtils.nextInt(100, 110))
        log.info("nextInt(100000, 100010): {}", RandomUtils.nextInt(100000, 100010))
        log.info("nextInt(100000, 100010): {}", RandomUtils.nextInt(100000, 100010))
        log.info("nextInt(100000, 100010): {}", RandomUtils.nextInt(100000, 100010))
    }

    @Test
    fun testRandomDouble() {
        log.info("nextDouble(1, 10): {}", RandomUtils.nextDouble(1, 10))
        log.info("nextDouble(1, 10): {}", RandomUtils.nextDouble(1, 10))
        log.info("nextDouble(1, 10): {}", RandomUtils.nextDouble(1, 10))
        log.info("nextDouble(100, 110): {}", RandomUtils.nextDouble(100, 110))
        log.info("nextDouble(100, 110): {}", RandomUtils.nextDouble(100, 110))
        log.info("nextDouble(100, 110): {}", RandomUtils.nextDouble(100, 110))
        log.info("nextDouble(100000, 100010): {}", RandomUtils.nextDouble(100000, 100010))
        log.info("nextDouble(100000, 100010): {}", RandomUtils.nextDouble(100000, 100010))
        log.info("nextDouble(100000, 100010): {}", RandomUtils.nextDouble(100000, 100010))
    }


    @Test
    fun testRandomAlphaNemeric() {
        log.info("nextAlphaNumeric(10): {}", RandomUtils.nextAlphaNumeric(10))
        log.info("nextAlphaNumeric(10): {}", RandomUtils.nextAlphaNumeric(10))
        log.info("nextAlphaNumeric(10): {}", RandomUtils.nextAlphaNumeric(10))
    }

    @Test
    fun testNextWord() {
        log.info("nextWord(10): {}", RandomUtils.nextWord(10))
        log.info("nextWord(10): {}", RandomUtils.nextWord(10))
        log.info("nextWord(10): {}", RandomUtils.nextWord(10))
    }

    enum class GENDER(
        val value: String
    ) {
        MALE("M"),
        FEMALE("F")
    }

    @Test
    fun test_nextArray() {
        log.info("nextArray(GENDER): {}", RandomUtils.nextArray(GENDER.values()).value)
        log.info("nextArray(GENDER): {}", RandomUtils.nextArray(GENDER.values()).value)
        log.info("nextArray(GENDER): {}", RandomUtils.nextArray(GENDER.values()).value)
        log.info("nextArray(GENDER): {}", RandomUtils.nextArray(GENDER.values()).value)
        log.info("nextArray(GENDER): {}", RandomUtils.nextArray(GENDER.values()).value)
    }

    @Test
    fun test_nextLocalDtmFromYear() {
        log.info("localDtm: {}", RandomUtils.nextLocalDtmFromYear(2010).format(DateUtils.FORMATTER))
        log.info("localDtm: {}", RandomUtils.nextLocalDtmFromYear(2010).format(DateUtils.FORMATTER))
        log.info("localDtm: {}", RandomUtils.nextLocalDtmFromYear(2010).format(DateUtils.FORMATTER))
        log.info("localDtm: {}", RandomUtils.nextLocalDtmFromYear(2010).format(DateUtils.FORMATTER))
        log.info("localDtm: {}", RandomUtils.nextLocalDtmFromYear(2010).format(DateUtils.FORMATTER))
    }

}