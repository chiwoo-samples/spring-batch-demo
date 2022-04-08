package example.chiwoo.demo.jobs.csvtoconsole

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemWriter

class ItemWriterConsole : ItemWriter<ItemModel> {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(javaClass)
    }

    override fun write(items: List<ItemModel>) {
        logger.info("Console item writer starts Chuck")
        for (item in items) {
            logger.info(item.toString())
        }
        logger.info("Console item writer ends Chuck")
    }
}