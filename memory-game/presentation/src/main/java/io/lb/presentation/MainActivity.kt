package io.lb.presentation

import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
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
import io.lb.presentation.ui.navigation.MemoryGameScreens
import io.lb.presentation.ui.theme.PokemonMemoryChallengeTheme
import io.lb.presentation.util.buildSoundPool
import io.lb.presentation.util.pauseMusic
import io.lb.presentation.util.playFlipEffect
import io.lb.presentation.util.playMatchEffect
import io.lb.presentation.util.playMusic
import io.lb.presentation.util.playPausedMusic

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var titleMediaPlayer: MediaPlayer
    private lateinit var gameMediaPlayer: MediaPlayer
    private lateinit var highScoresMediaPlayer: MediaPlayer
    private lateinit var gameOverMediaPlayer: MediaPlayer

    private lateinit var soundPool: SoundPool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        soundPool = buildSoundPool(this)

        titleMediaPlayer = MediaPlayer.create(this, R.raw.title_screen)
        gameMediaPlayer = MediaPlayer.create(this, R.raw.game_screen)
        highScoresMediaPlayer = MediaPlayer.create(this, R.raw.highscores_screen)
        gameOverMediaPlayer = MediaPlayer.create(this, R.raw.gameover_screen)

        setContent {
            PokemonMemoryChallengeTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = MemoryGameScreens.Menu.name
                ) {
                    composable(MemoryGameScreens.Menu.name) {
                        startMenuScreen(navController)
                    }
                    composable(
                        route = MemoryGameScreens.Game.name + "/{amount}",
                        arguments = listOf(
                            navArgument(name = "amount") {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        startGameScreen(navController)
                    }
                    composable(
                        route = MemoryGameScreens.HighScores.name,
                    ) {
                        startScoreScreen(navController)
                    }
                    composable(
                        route = MemoryGameScreens.GameOver.name + "/{score}",
                        arguments = listOf(
                            navArgument(name = "score") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        startGameOverScreen(backStackEntry, navController)
                    }
                }
            }
        }
    }

    @Composable
    private fun startMenuScreen(navController: NavHostController) {
        titleMediaPlayer.playMusic()
        gameOverMediaPlayer.pauseMusic()
        gameMediaPlayer.pauseMusic()
        highScoresMediaPlayer.pauseMusic()
        MenuScreen(
            navController = navController,
            onClickQuit = {
                finish()
            }
        )
    }

    @Composable
    private fun startGameScreen(navController: NavHostController) {
        gameMediaPlayer.playMusic()
        gameOverMediaPlayer.pauseMusic()
        titleMediaPlayer.pauseMusic()
        highScoresMediaPlayer.pauseMusic()
        GameScreen(
            navController = navController,
            onCardFlipped = {
                soundPool.playFlipEffect()
            },
            onCardMatched = {
                soundPool.playMatchEffect()
            }
        )
    }

    @Composable
    private fun startScoreScreen(navController: NavHostController) {
        highScoresMediaPlayer.playMusic()
        gameOverMediaPlayer.pauseMusic()
        titleMediaPlayer.pauseMusic()
        gameMediaPlayer.pauseMusic()
        ScoreScreen(navController = navController)
    }

    @Composable
    private fun startGameOverScreen(
        backStackEntry: NavBackStackEntry,
        navController: NavHostController
    ) {
        gameOverMediaPlayer.playMusic()
        titleMediaPlayer.pauseMusic()
        gameMediaPlayer.pauseMusic()
        highScoresMediaPlayer.pauseMusic()
        val score = backStackEntry.arguments?.getInt("score")
        GameOverScreen(
            navController = navController,
            score = score ?: 0
        )
    }

    override fun onResume() {
        super.onResume()
        titleMediaPlayer.playPausedMusic()
        gameMediaPlayer.playPausedMusic()
        highScoresMediaPlayer.playPausedMusic()
        gameOverMediaPlayer.playPausedMusic()
    }

    override fun onPause() {
        super.onPause()
        titleMediaPlayer.pauseMusic()
        gameMediaPlayer.pauseMusic()
        highScoresMediaPlayer.pauseMusic()
        gameOverMediaPlayer.pauseMusic()
    }

    override fun onDestroy() {
        super.onDestroy()
        titleMediaPlayer.release()
        gameMediaPlayer.release()
        highScoresMediaPlayer.release()
        gameOverMediaPlayer.release()
        soundPool.release()
    }
}
