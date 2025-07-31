package jp.terakoyalabo.test.infrastructure.interaction

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jp.terakoyalabo.common.exception.infrastructure.DocumentCreateFailedException
import jp.terakoyalabo.common.exception.infrastructure.DocumentDeleteFailedException
import jp.terakoyalabo.common.exception.infrastructure.DocumentUpdateFailedException
import jp.terakoyalabo.infrastructure.database.common.dto.BaseProfileCollection
import jp.terakoyalabo.infrastructure.database.interaction.BaseProfileInteraction
import org.bson.conversions.Bson
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import kotlin.jvm.java

class BaseProfileInteractionTest {
    private val database = mockk<MongoDatabase>()
    private val collection = mockk<MongoCollection<BaseProfileCollection>>()
    private lateinit var interaction: BaseProfileInteraction

    private val sampleProfile = BaseProfileCollection(
        userId = "user1",
        firstName = "Taro",
        familyName = "Yamada",
        firstNameKana = "タロウ",
        familyNameKana = "ヤマダ",
        displayName = "Taro Y.",
        disabled = false,
        createdAt = 1000L,
        updatedAt = 1000L,
        createdBy = "user1",
        updatedBy = "user1"
    )

    @BeforeEach
    fun setUp() {
        interaction = BaseProfileInteraction(database = database, client = TODO())
        every { database.getCollection<BaseProfileCollection>("test_base_profile") }
    }

    @Test
    fun `createBaseProfile throws if active document exists`() {
        every {
            collection.findOne(any<Bson>())
        } returns sampleProfile

        val ex = Assertions.assertThrows(DocumentCreateFailedException::class.java) {
            interaction.createBaseProfile(sampleProfile)
        }
        Assertions.assertEquals(
            "An active document with the specified information already exists. cannot create a new document.",
            ex.message
        )
        verify(exactly = 0) { collection.insertOne(any()) }
    }
    @Test
    fun `createBaseProfile inserts when no active document`() {
        every {
            collection.findOne(any<Bson>())
        } returns null

        val insertResult = mockk<InsertOneResult>()
        every { collection.insertOne(sampleProfile) } returns insertResult

        val result = interaction.createBaseProfile(sampleProfile)
        Assertions.assertSame(insertResult, result)
    }

    @Test
    fun `referenceBaseProfile returns document`() {
        every { collection.findOne(any<Bson>()) } returns sampleProfile

        val result = interaction.referenceBaseProfile("user1")
        Assertions.assertEquals(sampleProfile, result)
    }

    @Test
    fun `updateBaseProfile throws if no document`() {
        every { collection.findOne(any<Bson>()) } returns null

        val ex = Assertions.assertThrows(DocumentUpdateFailedException::class.java) {
            interaction.updateBaseProfile(sampleProfile)
        }
        Assertions.assertEquals("No document found for update.", ex.message)
    }

    @Test
    fun `updateBaseProfile calls updateOne if found`() {
        every { collection.findOne(any<Bson>()) } returns sampleProfile
        val updateResult = mockk<UpdateResult>()
        every { collection.updateOne(any<Bson>(), any<Bson>()) } returns updateResult

        val result = interaction.updateBaseProfile(sampleProfile)
        Assertions.assertSame(updateResult, result)
    }

    @Test
    fun `deleteLogicallyBaseProfile throws if no document`() {
        every { collection.findOne(any<Bson>()) } returns null

        val ex = Assertions.assertThrows(DocumentDeleteFailedException::class.java) {
            interaction.deleteLogicallyBaseProfile("user1", 123L)
        }
        Assertions.assertEquals("No document found for delete logically.", ex.message)
    }

    @Test
    fun `deleteLogicallyBaseProfile calls updateOne if found`() {
        every { collection.findOne(any<Bson>()) } returns sampleProfile
        val updateResult = mockk<UpdateResult>()
        every { collection.updateOne(any<Bson>(), any<Bson>()) } returns updateResult

        val result = interaction.deleteLogicallyBaseProfile("user1", 123L)
        Assertions.assertSame(updateResult, result)
    }

    @Test
    fun `deletePhysicallyBaseProfile throws if no document`() {
        every { collection.findOne(any<Bson>()) } returns null

        val ex = Assertions.assertThrows(DocumentDeleteFailedException::class.java) {
            interaction.deletePhysicallyBaseProfile("user1")
        }
        Assertions.assertEquals("No document found for delete physically.", ex.message)
    }

    @Test
    fun `deletePhysicallyBaseProfile calls deleteOne if found`() {
        every { collection.findOne(any<Bson>()) } returns sampleProfile
        val deleteResult = mockk<DeleteResult>()
        every { collection.deleteOne(any<Bson>()) } returns deleteResult

        val result = interaction.deletePhysicallyBaseProfile("user1")
        Assertions.assertSame(deleteResult, result)
    }
}
