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
)
