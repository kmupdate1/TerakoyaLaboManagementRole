package jp.terakoyalabo.test.infrastructure.web.interaction

import jp.terakoyalabo.infrastructure.web.interaction.PermissionInteraction
import org.junit.jupiter.api.Test
import org.litote.kmongo.json

class TestPermission {
    @Test
    fun testRead() {
        val res = PermissionInteraction(TODO()).create(TODO())
        println(res.json)
    }
}
