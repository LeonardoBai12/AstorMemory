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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.edit
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.lb.presentation.game.GameScreen
import io.lb.presentation.gameover.GameOverScreen
import io.lb.presentation.menu.MenuScreen
import io.lb.presentation.scores.ScoreScreen
import io.lb.presentation.settings.SettingsScreen
import io.lb.presentation.ui.navigation.MemoryGameScreens
import io.lb.presentation.ui.theme.AstorMemoryChallengeTheme
import io.lb.presentation.util.buildSoundPool
import io.lb.presentation.util.gameOverArgs
import io.lb.presentation.util.gameScreenArgs
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
        initVariables()
        setContent {
            val navController = rememberNavController()
            val isDarkMode = localDarkMode()
            val isDarkModeState = remember {
                mutableStateOf(sharedPref.getBoolean("darkMode", isDarkMode))
            }
            AstorMemoryChallengeTheme(isDarkModeState.value) {
                Surface {
                    NavHost(
                        navController = navController,
                        startDestination = MemoryGameScreens.Menu.name
                    ) {
                        composable(MemoryGameScreens.Menu.name) {
                            StartMenuScreen(
                                navController = navController,
                                isDarkMode = localDarkMode()
                            )
                        }
                        composable(MemoryGameScreens.Settings.name) {
                            SettingsScreenNav(navController, isDarkModeState)
                        }
                        composable(
                            route = MemoryGameScreens.Game.name + "/{amount}",
                            arguments = gameScreenArgs()
                        ) { backStackEntry ->
                            BackHandler(navController)
                            StartGameScreen(
                                backStackEntry,
                                navController
                            )
                        }
                        composable(
                            route = MemoryGameScreens.HighScores.name,
                        ) {
                            StartScoreScreen(
                                navController = navController,
                                isDarkMode = localDarkMode(),
                            )
                        }
                        composable(
                            route = MemoryGameScreens.GameOver.name + "/{score}/{amount}",
                            arguments = gameOverArgs()
                        ) { backStackEntry ->
                            StartGameOverScreen(
                                backStackEntry = backStackEntry,
                                navController = navController,
                                isDarkMode = localDarkMode(),
                            )
                        }
                    }
                }
            }
        }
    }

    private fun initVariables() {
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
    }

    @Composable
    private fun SettingsScreenNav(
        navController: NavHostController,
        isDarkModeState: MutableState<Boolean>
    ) {
        BackHandler(navController)
        SettingsScreen(
            navController = navController,
            cardsPerLine = sharedPref.getInt("cardsPerLine", 4),
            cardsPerColumn = sharedPref.getInt("cardsPerColumn", 6),
            isDarkMode = localDarkMode(),
            onChangeDarkMode = { darkMode ->
                sharedPref.edit { putBoolean("darkMode", darkMode) }
                isDarkModeState.value = darkMode
            },
            onChangeCardsPerLine = { cardsPerLine ->
                sharedPref.edit { putInt("cardsPerLine", cardsPerLine) }
            },
            onChangeCardsPerColumn = { cardsPerColumn ->
                sharedPref.edit {
                    putInt("cardsPerColumn", cardsPerColumn)
                }
            }
        )
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
    private fun StartMenuScreen(
        navController: NavHostController,
        isDarkMode: Boolean
    ) {
        if (sharedPref.getBoolean("isMuted", false).not()) {
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
        }
        MenuScreen(
            navController = navController,
            isDarkMode = isDarkMode,

            initialAmount = sharedPref.getInt("amount", 6),
            onChangeAmount = {
                sharedPref.edit { putInt("amount", it) }
            },
            isMuted = sharedPref.getBoolean("isMuted", false),
            onChangeMuted = {
                sharedPref.edit { putBoolean("isMuted", it) }
                if (it) {
                    pauseAll()
                } else {
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
                }
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
        if (sharedPref.getBoolean("isMuted", false).not()) {
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
                trainerBattleMediaPlayer.playMusic(0.85f)
                gymLeaderBattleMediaPlayer.pauseMusic()
                victoryRoadMediaPlayer.pauseMusic()
                eliteFourBattleMediaPlayer.pauseMusic()
            }

            gameOverMediaPlayer.pauseMusic()
            lavenderMediaPlayer.pauseMusic()
            titleMediaPlayer.pauseMusic()
            highScoresMediaPlayer.pauseMusic()
            finalVictoryMediaPlayer.pauseMusic()
        }
        GameScreen(
            navController = navController,
            isDarkMode = localDarkMode(),
            cardsPerLine = sharedPref.getInt("cardsPerLine", 4),
            cardsPerColumn = sharedPref.getInt("cardsPerColumn", 6),
            onCardFlipped = {
                soundPool.playFlipEffect(sharedPref.getBoolean("isMuted", false))
            },
            onCardMatched = { matches ->
                if (matches > amount * 3 / 4) {
                    soundPool.playMatchEffect(sharedPref.getBoolean("isMuted", false))
                } else {
                    soundPool.playInitialMatchEffect(sharedPref.getBoolean("isMuted", false))
                }
            }
        )
    }

    @Composable
    private fun localDarkMode(
        isDarkMode: Boolean = isSystemInDarkTheme()
    ): Boolean = sharedPref.getBoolean(
        "darkMode",
        isDarkMode
    )

    @Composable
    private fun StartScoreScreen(
        navController: NavHostController,
        isDarkMode: Boolean
    ) {
        BackHandler(navController)
        if (sharedPref.getBoolean("isMuted", false).not()) {
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
        }
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
        BackHandler(navController)
        val score = backStackEntry.arguments?.getInt("score")
        val amount = backStackEntry.arguments?.getInt("amount") ?: 6
        if (sharedPref.getBoolean("isMuted", false).not()) {
            titleMediaPlayer.pauseMusic()
            wildMediaPlayer.pauseMusic()
            highScoresMediaPlayer.pauseMusic()
            trainerBattleMediaPlayer.pauseMusic()
            gymLeaderBattleMediaPlayer.pauseMusic()
            eliteFourBattleMediaPlayer.pauseMusic()
            victoryRoadMediaPlayer.pauseMusic()
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
        playAllPaused()
    }

    private fun playAllPaused() {
        if (sharedPref.getBoolean("isMuted", false).not()) {
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
    }

    override fun onPause() {
        super.onPause()
        pauseAll()
    }

    private fun pauseAll() {
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
        if (sharedPref.getBoolean("isMuted", false).not()) {
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
}
