package example.chiwoo.demo.jobs.hellolistener

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.ChunkListener
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.batch.core.scope.context.ChunkContext

class HelloChunkListener : ChunkListener {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(javaClass)
    }

    override fun beforeChunk(context: ChunkContext) {
        log.info("beforeChunk: {}", context)
    }

    override fun afterChunk(context: ChunkContext) {
        log.info("afterChunk: {}", context)
    }

    override fun afterChunkError(context: ChunkContext) {
        log.info("afterChunkError: {}", context)
    }

}