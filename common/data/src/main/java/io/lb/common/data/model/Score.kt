package io.lb.common.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Score(
    val score: Int,
    val timeMillis: Long,
)
