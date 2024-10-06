package io.lb.common.shared.flow

/**
 * A generic class that holds a value with its loading status.
 *
 * @property T The type of the value.
 * @property data The value.
 * @property message The message.
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}
