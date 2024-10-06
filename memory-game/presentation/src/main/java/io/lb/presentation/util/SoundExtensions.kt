package io.lb.presentation.util

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
import io.lb.presentation.R

private var pausedMediaPlayer: String? = null

fun MediaPlayer.playMusic() {
    isLooping = true

    if (isPlaying.not()) {
        seekTo(0)
        start()
    }
}

fun MediaPlayer.playPausedMusic() {
    if (pausedMediaPlayer == this.toString() && isPlaying.not()) {
        start()
    }
}

fun MediaPlayer.pauseMusic() {
    if (isPlaying) {
        pausedMediaPlayer = this.toString()
        pause()
    }
}

fun MediaPlayer.stopMusic() {
    if (isPlaying) {
        stop()
    }
}

private fun SoundPool.playEffect(soundId: Int, volume: Float = 1f) {
    play(
        soundId,
        volume,
        volume,
        1,
        0,
        1f
    )
}

private var flipCardEffectId = 0
private var matchEffectId = 0

fun buildSoundPool(context: Context): SoundPool {
    val soundPool = SoundPool.Builder()
        .setMaxStreams(1)
        .build()

    flipCardEffectId = soundPool.load(context, R.raw.flip_card_effect, 1)
    matchEffectId = soundPool.load(context, R.raw.match_effect, 1)

    return soundPool
}

fun SoundPool.playFlipEffect() {
    playEffect(flipCardEffectId)
}

fun SoundPool.playMatchEffect() {
    playEffect(matchEffectId, 0.75f)
}
