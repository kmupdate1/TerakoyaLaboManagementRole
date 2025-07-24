package jp.terakoyalabo.infrastructure.database.common.util

import com.mongodb.event.CommandFailedEvent
import com.mongodb.event.CommandListener
import com.mongodb.event.CommandStartedEvent
import com.mongodb.event.CommandSucceededEvent
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit

class MongoDBMonitoring: CommandListener {
    private val logger = LoggerFactory.getLogger(MongoDBMonitoring::class.java)

    override fun commandStarted(event: CommandStartedEvent) {
        super.commandStarted(event)
        logger.info(
            "STARTING to MongoDB Operation({}) ID:[{}]",
            event.commandName,
            event.operationId,
        )
    }

    override fun commandSucceeded(event: CommandSucceededEvent) {
        super.commandSucceeded(event)
        logger.info(
            "SUCCEEDED to MongoDB Operation({}) ID:[{}] - {}ms.",
            event.commandName,
            event.operationId,
            event.getElapsedTime(TimeUnit.MILLISECONDS),
        )
    }

    override fun commandFailed(event: CommandFailedEvent) {
        super.commandFailed(event)
        logger.error(
            "FAILED to MongoDB Operation({}) ID:[{}] - {}ms.",
            event.commandName,
            event.operationId,
            event.getElapsedTime(TimeUnit.MILLISECONDS),
            event.throwable,
        )
    }
}
