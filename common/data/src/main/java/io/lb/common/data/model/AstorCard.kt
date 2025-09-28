package io.lb.common.data.model

import kotlinx.serialization.Serializable

/**
 * Data class representing a Astor.
 *
 * @property id The ID of the Astor.
 * @property astorId The ID of the Astor.
 * @property imageUrl The URL of the image of the Astor.
 * @property imageData The data of the image of the Astor.
 * @property name The name of the Astor.
 */
@Serializable
data class AstorCard(
    val id: String,
    val astorId: Int,
    val imageUrl: String,
    val imageData: ByteArray,
    val name: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AstorCard

        if (astorId != other.astorId) return false
        if (id != other.id) return false
        if (imageUrl != other.imageUrl) return false
        if (!imageData.contentEquals(other.imageData)) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = astorId
        result = 31 * result + id.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + imageData.contentHashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}
