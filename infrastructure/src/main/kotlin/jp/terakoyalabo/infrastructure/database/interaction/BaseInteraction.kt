package jp.terakoyalabo.infrastructure.database.interaction

import com.mongodb.client.model.Filters
import org.litote.kmongo.and

open class BaseInteraction {
    protected fun enabledFilter(userId: String) = and(Filters.eq("user_id", userId), Filters.eq("disabled", false))
    protected fun disabledFilter(userId: String) = and(Filters.eq("user_id", userId), Filters.eq("disabled", true))
}
