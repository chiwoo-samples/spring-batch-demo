package example.chiwoo.demo.jobs.hello03

import example.chiwoo.demo.jobs.JobLogger
import org.springframework.batch.core.ChunkListener
import org.springframework.batch.core.scope.context.ChunkContext

class HelloChunkListener : ChunkListener {

    override fun beforeChunk(context: ChunkContext) {
        JobLogger.log.info("beforeChunk: {}", context)
    }

    override fun afterChunk(context: ChunkContext) {
        JobLogger.log.info("afterChunk: {}", context)
    }

    override fun afterChunkError(context: ChunkContext) {
        JobLogger.log.info("afterChunkError: {}", context)
    }

}