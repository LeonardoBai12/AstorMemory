package io.lb.presentation

import android.content.SharedPreferences
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import io.lb.presentation.game.GameScreen
import io.lb.presentation.gameover.GameOverScreen
import io.lb.presentation.menu.MenuScreen
import io.lb.presentation.scores.ScoreScreen
import io.lb.presentation.settings.SettingsScreen
import io.lb.presentation.ui.navigation.MemoryGameScreens
import io.lb.presentation.ui.theme.PokemonMemoryChallengeTheme
import io.lb.presentation.util.buildSoundPool
import io.lb.presentation.util.pauseMusic
import io.lb.presentation.util.playFlipEffect
import io.lb.presentation.util.playInitialMatchEffect
import io.lb.presentation.util.playMatchEffect
import io.lb.presentation.util.playMusic
import io.lb.presentation.util.playPausedMusic

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var titleMediaPlayer: MediaPlayer
    private lateinit var wildMediaPlayer: MediaPlayer
    private lateinit var trainerBattleMediaPlayer: MediaPlayer
    private lateinit var gymLeaderBattleMediaPlayer: MediaPlayer
    private lateinit var eliteFourBattleMediaPlayer: MediaPlayer
    private lateinit var highScoresMediaPlayer: MediaPlayer
    private lateinit var gameOverMediaPlayer: MediaPlayer
    private lateinit var victoryRoadMediaPlayer: MediaPlayer
    private lateinit var lavenderMediaPlayer: MediaPlayer
    private lateinit var finalVictoryMediaPlayer: MediaPlayer
    private lateinit var sharedPref: SharedPreferences

    private lateinit var soundPool: SoundPool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        soundPool = buildSoundPool(this)
        sharedPref = getSharedPreferences("memory_game", MODE_PRIVATE)

        titleMediaPlayer = MediaPlayer.create(this, R.raw.title_screen)
        wildMediaPlayer = MediaPlayer.create(this, R.raw.wild)
        trainerBattleMediaPlayer = MediaPlayer.create(this, R.raw.trainer)
        gymLeaderBattleMediaPlayer = MediaPlayer.create(this, R.raw.gym_leader)
        eliteFourBattleMediaPlayer = MediaPlayer.create(this, R.raw.final_battle)
        highScoresMediaPlayer = MediaPlayer.create(this, R.raw.highscores_screen)
        gameOverMediaPlayer = MediaPlayer.create(this, R.raw.gameover_screen)
        victoryRoadMediaPlayer = MediaPlayer.create(this, R.raw.victory_road)
        lavenderMediaPlayer = MediaPlayer.create(this, R.raw.lavender)
        finalVictoryMediaPlayer = MediaPlayer.create(this, R.raw.victory_final)

        setContent {
            val navController = rememberNavController()
            val isDarkMode = sharedPref.getBoolean("darkMode", isSystemInDarkTheme())
            val isDarkModeState = remember {
                mutableStateOf(sharedPref.getBoolean("darkMode", isDarkMode))
            }

            PokemonMemoryChallengeTheme(isDarkModeState.value) {
                Surface {
                    NavHost(
                        navController = navController,
                        startDestination = MemoryGameScreens.Menu.name
                    ) {
                        composable(MemoryGameScreens.Menu.name) {
                            StartMenuScreen(
                                navController = navController,
                                isDarkMode = sharedPref.getBoolean(
                                    "darkMode",
                                    isSystemInDarkTheme()
                                )
                            )
                        }
                        composable(MemoryGameScreens.Settings.name) {
                            BackHandler(navController)
                            SettingsScreen(
                                navController = navController,
                                cardsPerLine = sharedPref.getInt("cardsPerLine", 4),
                                cardsPerColumn = sharedPref.getInt("cardsPerColumn", 6),
                                isDarkMode = sharedPref.getBoolean(
                                    "darkMode",
                                    isSystemInDarkTheme()
                                ),
                                onChangeDarkMode = { darkMode ->
                                    sharedPref.edit().putBoolean("darkMode", darkMode).apply()
                                    isDarkModeState.value = darkMode
                                },
                                onChangeCardsPerLine = { cardsPerLine ->
                                    sharedPref.edit().putInt("cardsPerLine", cardsPerLine).apply()
                                },
                                onChangeCardsPerColumn = { cardsPerColumn ->
                                    sharedPref.edit().putInt("cardsPerColumn", cardsPerColumn)
                                        .apply()
                                }
                            )
                        }
                        composable(
                            route = MemoryGameScreens.Game.name + "/{amount}",
                            arguments = listOf(
                                navArgument(name = "amount") {
                                    type = NavType.IntType
                                }
                            )
                        ) { backStackEntry ->
                            BackHandler(navController)
                            StartGameScreen(
                                backStackEntry, navController
                            )
                        }
                        composable(
                            route = MemoryGameScreens.HighScores.name,
                        ) {
                            BackHandler(navController)
                            StartScoreScreen(
                                navController = navController,
                                isDarkMode = sharedPref.getBoolean(
                                    "darkMode",
                                    isSystemInDarkTheme()
                                ),
                            )
                        }
                        composable(
                            route = MemoryGameScreens.GameOver.name + "/{score}/{amount}",
                            arguments = listOf(
                                navArgument(name = "score") {
                                    type = NavType.IntType
                                },
                                navArgument(name = "amount") {
                                    type = NavType.IntType
                                }
                            )
                        ) { backStackEntry ->
                            BackHandler(navController)
                            StartGameOverScreen(
                                backStackEntry = backStackEntry,
                                navController = navController,
                                isDarkMode = sharedPref.getBoolean(
                                    "darkMode",
                                    isSystemInDarkTheme()
                                ),
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun BackHandler(navController: NavHostController) {
        BackHandler {
            navController.navigate(MemoryGameScreens.Menu.name) {
                popUpTo(MemoryGameScreens.Menu.name) {
                    inclusive = true
                }
            }
        }
    }

    @Composable
    private fun StartMenuScreen(navController: NavHostController, isDarkMode: Boolean) {
        titleMediaPlayer.playMusic()
        gameOverMediaPlayer.pauseMusic()
        wildMediaPlayer.pauseMusic()
        highScoresMediaPlayer.pauseMusic()
        trainerBattleMediaPlayer.pauseMusic()
        gymLeaderBattleMediaPlayer.pauseMusic()
        eliteFourBattleMediaPlayer.pauseMusic()
        victoryRoadMediaPlayer.pauseMusic()
        lavenderMediaPlayer.pauseMusic()
        finalVictoryMediaPlayer.pauseMusic()
        MenuScreen(
            navController = navController,
            isDarkMode = isDarkMode,
            initialAmount = sharedPref.getInt("amount", 6),
            onChangeAmount = {
                sharedPref.edit().putInt("amount", it).apply()
            },
            onClickQuit = {
                finish()
            }
        )
    }

    @Composable
    private fun StartGameScreen(
        backStackEntry: NavBackStackEntry,
        navController: NavHostController,
    ) {
        val amount = backStackEntry.arguments?.getInt("amount") ?: 6

        if (amount == 1) {
            wildMediaPlayer.pauseMusic()
            trainerBattleMediaPlayer.pauseMusic()
            gymLeaderBattleMediaPlayer.pauseMusic()
            victoryRoadMediaPlayer.playMusic()
            eliteFourBattleMediaPlayer.pauseMusic()
        } else if (amount < 8) {
            wildMediaPlayer.playMusic()
            trainerBattleMediaPlayer.pauseMusic()
            gymLeaderBattleMediaPlayer.pauseMusic()
            victoryRoadMediaPlayer.pauseMusic()
            eliteFourBattleMediaPlayer.pauseMusic()
        } else if (amount >= 20) {
            wildMediaPlayer.pauseMusic()
            trainerBattleMediaPlayer.pauseMusic()
            gymLeaderBattleMediaPlayer.pauseMusic()
            victoryRoadMediaPlayer.pauseMusic()
            eliteFourBattleMediaPlayer.playMusic(0.85f)
        } else if (amount >= 12) {
            wildMediaPlayer.pauseMusic()
            trainerBattleMediaPlayer.pauseMusic()
            gymLeaderBattleMediaPlayer.playMusic(0.85f)
            eliteFourBattleMediaPlayer.pauseMusic()
            victoryRoadMediaPlayer.pauseMusic()
        } else {
            wildMediaPlayer.pauseMusic()
            trainerBattleMediaPlayer.playMusic(0.95f)
            gymLeaderBattleMediaPlayer.pauseMusic()
            victoryRoadMediaPlayer.pauseMusic()
            eliteFourBattleMediaPlayer.pauseMusic()
        }

        gameOverMediaPlayer.pauseMusic()
        lavenderMediaPlayer.pauseMusic()
        titleMediaPlayer.pauseMusic()
        highScoresMediaPlayer.pauseMusic()
        finalVictoryMediaPlayer.pauseMusic()
        GameScreen(
            navController = navController,
            isDarkMode = sharedPref.getBoolean("darkMode", isSystemInDarkTheme()),
            cardsPerLine = sharedPref.getInt("cardsPerLine", 4),
            cardsPerColumn = sharedPref.getInt("cardsPerColumn", 6),
            onCardFlipped = {
                soundPool.playFlipEffect()
            },
            onCardMatched = { matches ->
                if (matches > amount * 3 / 4) {
                    soundPool.playMatchEffect()
                } else {
                    soundPool.playInitialMatchEffect()
                }
            }
        )
    }

    @Composable
    private fun StartScoreScreen(
        navController: NavHostController,
        isDarkMode: Boolean
    ) {
        highScoresMediaPlayer.playMusic()
        lavenderMediaPlayer.pauseMusic()
        gameOverMediaPlayer.pauseMusic()
        titleMediaPlayer.pauseMusic()
        wildMediaPlayer.pauseMusic()
        trainerBattleMediaPlayer.pauseMusic()
        gymLeaderBattleMediaPlayer.pauseMusic()
        eliteFourBattleMediaPlayer.pauseMusic()
        victoryRoadMediaPlayer.pauseMusic()
        finalVictoryMediaPlayer.pauseMusic()
        ScoreScreen(
            navController = navController,
            isDarkMode = isDarkMode
        )
    }

    @Composable
    private fun StartGameOverScreen(
        backStackEntry: NavBackStackEntry,
        isDarkMode: Boolean,
        navController: NavHostController
    ) {
        titleMediaPlayer.pauseMusic()
        wildMediaPlayer.pauseMusic()
        highScoresMediaPlayer.pauseMusic()
        trainerBattleMediaPlayer.pauseMusic()
        gymLeaderBattleMediaPlayer.pauseMusic()
        eliteFourBattleMediaPlayer.pauseMusic()
        victoryRoadMediaPlayer.pauseMusic()
        val score = backStackEntry.arguments?.getInt("score")
        val amount = backStackEntry.arguments?.getInt("amount") ?: 6
        if (score == 0) {
            lavenderMediaPlayer.playMusic(0.95f)
            gameOverMediaPlayer.pauseMusic()
            finalVictoryMediaPlayer.pauseMusic()
        } else if (amount >= 20) {
            lavenderMediaPlayer.pauseMusic()
            gameOverMediaPlayer.pauseMusic()
            finalVictoryMediaPlayer.playMusic()
        } else {
            lavenderMediaPlayer.pauseMusic()
            finalVictoryMediaPlayer.pauseMusic()
            gameOverMediaPlayer.playMusic()
        }
        GameOverScreen(
            navController = navController,
            score = score ?: 0,
            isDarkMode = isDarkMode,
            amount = amount,
        )
    }

    override fun onResume() {
        super.onResume()
        titleMediaPlayer.playPausedMusic()
        wildMediaPlayer.playPausedMusic()
        trainerBattleMediaPlayer.playPausedMusic()
        gymLeaderBattleMediaPlayer.playPausedMusic()
        eliteFourBattleMediaPlayer.playPausedMusic()
        highScoresMediaPlayer.playPausedMusic()
        victoryRoadMediaPlayer.playPausedMusic()
        gameOverMediaPlayer.playPausedMusic()
        lavenderMediaPlayer.playPausedMusic()
        finalVictoryMediaPlayer.playPausedMusic()
    }

    override fun onPause() {
        super.onPause()
        titleMediaPlayer.pauseMusic()
        wildMediaPlayer.pauseMusic()
        trainerBattleMediaPlayer.pauseMusic()
        gymLeaderBattleMediaPlayer.pauseMusic()
        eliteFourBattleMediaPlayer.pauseMusic()
        highScoresMediaPlayer.pauseMusic()
        victoryRoadMediaPlayer.pauseMusic()
        gameOverMediaPlayer.pauseMusic()
        lavenderMediaPlayer.pauseMusic()
        finalVictoryMediaPlayer.pauseMusic()
    }

    override fun onDestroy() {
        super.onDestroy()
        titleMediaPlayer.release()
        wildMediaPlayer.release()
        trainerBattleMediaPlayer.release()
        gymLeaderBattleMediaPlayer.release()
        victoryRoadMediaPlayer.release()
        eliteFourBattleMediaPlayer.release()
        highScoresMediaPlayer.release()
        gameOverMediaPlayer.release()
        lavenderMediaPlayer.release()
        finalVictoryMediaPlayer.release()
        soundPool.release()
    }
}
