package io.lb.common.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Score(
    val score: Int,
    val amount: Int,
    val timeMillis: Long,
)
