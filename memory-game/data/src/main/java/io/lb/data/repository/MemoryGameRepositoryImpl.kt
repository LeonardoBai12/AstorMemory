package io.lb.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.lb.common.data.model.AstorCard
import io.lb.common.data.model.Score
import io.lb.data.R
import io.lb.data.datasource.MemoryGameDataSource
import io.lb.domain.repository.MemoryGameRepository
import java.io.ByteArrayOutputStream
import java.util.Locale
import javax.inject.Inject

/**
 * Implementation of [MemoryGameRepository] that fetches data from the network.
 *
 * @property dataSource The data source to fetch data from.
 */
internal class MemoryGameRepositoryImpl @Inject constructor(
    private val context: Context,
    private val dataSource: MemoryGameDataSource
) : MemoryGameRepository {
    override suspend fun getAstorPairs(amount: Int): List<AstorCard> {
        val astorCards = getAllAstorCards(context)
        val selectedAstor = astorCards.take(amount).shuffled().toMutableList()
        selectedAstor.addAll(selectedAstor.shuffled())
        return selectedAstor.shuffled()
    }

    private fun getAllAstorCards(context: Context): List<AstorCard> {
        val drawableFieldNames = R.drawable::class.java.fields
        val astorCards = mutableListOf<AstorCard>()

        for (field in drawableFieldNames) {
            val resourceName = field.name

            val regex = Regex("([a-zA-Z_]+)_(\\d+)")
            val matchResult = regex.matchEntire(resourceName)

            if (matchResult != null) {
                val (nameWithUnderscores, id) = matchResult.destructured
                val name = nameWithUnderscores.replace("_", " ")
                    .split(" ")
                    .joinToString(" ") { it.capitalize(Locale.ROOT) }

                val resourceId = field.getInt(null)
                val imageUrl = "android.resource://${context.packageName}/drawable/$resourceName"

                val imageData = context.resources.openRawResource(resourceId).use { inputStream ->
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    val outputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
                    outputStream.toByteArray()
                }

                astorCards.add(
                    AstorCard(
                        id = id,
                        astorId = id.toInt(),
                        imageUrl = imageUrl,
                        imageData = imageData,
                        name = name
                    )
                )
            }
        }
        return astorCards.shuffled()
    }

    override suspend fun getScores(): List<Score> {
        return dataSource.getScores()
    }

    override suspend fun getScoresByAmount(amount: Int): List<Score> {
        return dataSource.getScoresByAmount(amount)
    }

    override suspend fun insertScore(score: Int, amount: Int) {
        dataSource.insertScore(score, amount)
    }
}
