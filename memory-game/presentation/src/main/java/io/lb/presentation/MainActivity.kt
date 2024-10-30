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
import io.lb.presentation.util.playInitialMatchEffect
import io.lb.presentation.util.playMatchEffect
import io.lb.presentation.util.playMusic
import io.lb.presentation.util.playPausedMusic

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

    private lateinit var soundPool: SoundPool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        soundPool = buildSoundPool(this)

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
            PokemonMemoryChallengeTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = MemoryGameScreens.Menu.name
                ) {
                    composable(MemoryGameScreens.Menu.name) {
                        StartMenuScreen(navController)
                    }
                    composable(
                        route = MemoryGameScreens.Game.name + "/{amount}",
                        arguments = listOf(
                            navArgument(name = "amount") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        StartGameScreen(backStackEntry, navController)
                    }
                    composable(
                        route = MemoryGameScreens.HighScores.name,
                    ) {
                        StartScoreScreen(navController)
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
                        StartGameOverScreen(backStackEntry, navController)
                    }
                }
            }
        }
    }

    @Composable
    private fun StartMenuScreen(navController: NavHostController) {
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
            onClickQuit = {
                finish()
            }
        )
    }

    @Composable
    private fun StartGameScreen(
        backStackEntry: NavBackStackEntry,
        navController: NavHostController
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
        } else if (amount == 20) {
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
    private fun StartScoreScreen(navController: NavHostController) {
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
        ScoreScreen(navController = navController)
    }

    @Composable
    private fun StartGameOverScreen(
        backStackEntry: NavBackStackEntry,
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
        val amount = backStackEntry.arguments?.getInt("amount")
        if (score == 0) {
            lavenderMediaPlayer.playMusic(0.95f)
            gameOverMediaPlayer.pauseMusic()
            finalVictoryMediaPlayer.pauseMusic()
        } else if (amount == 20) {
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
            score = score ?: 0
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
