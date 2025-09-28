package io.lb.presentation.util

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

internal fun gameScreenArgs(): List<NamedNavArgument> = listOf(
    navArgument(name = "amount") {
        type = NavType.IntType
    }
)

internal fun gameOverArgs(): List<NamedNavArgument> = listOf(
    navArgument(name = "score") {
        type = NavType.IntType
    },
    navArgument(name = "amount") {
        type = NavType.IntType
    }
)
