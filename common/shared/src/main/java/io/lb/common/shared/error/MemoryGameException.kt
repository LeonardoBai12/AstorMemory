package io.lb.common.shared.error

/**
 * Exception thrown by the memory game.
 *
 * @property code The error code.
 * @property message The error message.
 */
data class MemoryGameException(
    val code: Int,
    override val message: String?
) : Exception()
