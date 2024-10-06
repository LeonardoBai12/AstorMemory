package io.lb.impl.ktor.client.util

import io.ktor.client.HttpClient
import io.lb.common.data.model.Pokemon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Extension function to make a request to the API.
 *
 * @throws IllegalArgumentException If the base URL does not start with "https://".
 *
 * @return The response from the API.
 */
internal suspend fun HttpClient.request(
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    amount: Int
): List<Pokemon> = withContext(coroutineDispatcher) {
        return@withContext emptyList<Pokemon>()
    //val response = get {
//
    //}
}

