package io.lb.presentation.util

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
import io.lb.presentation.R

private var pausedMediaPlayer: String? = null

fun MediaPlayer.playMusic(volume: Float = MATCH_VOLUME) {
    isLooping = true
    setVolume(volume, volume)

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

private fun SoundPool.playEffect(soundId: Int, volume: Float = MATCH_VOLUME) {
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
private var flipInitialCardsEffectId = 0

fun buildSoundPool(context: Context): SoundPool {
    val soundPool = SoundPool.Builder()
        .setMaxStreams(1)
        .build()

    flipCardEffectId = soundPool.load(context, R.raw.flip_card_final, 1)
    matchEffectId = soundPool.load(context, R.raw.match_effect, 1)
    flipInitialCardsEffectId = soundPool.load(context, R.raw.match_card_init, 1)

    return soundPool
}

fun SoundPool.playFlipEffect(isMuted: Boolean) {
    if (isMuted) {
        playEffect(flipCardEffectId, FLIP_VOLUME / 2)
    } else {
        playEffect(flipCardEffectId, FLIP_VOLUME)
    }
}

fun SoundPool.playInitialMatchEffect(isMuted: Boolean) {
    if (isMuted) {
        playEffect(flipInitialCardsEffectId, MATCH_VOLUME / 2)
    } else {
        playEffect(flipInitialCardsEffectId, MATCH_VOLUME)
    }
}

fun SoundPool.playMatchEffect(isMuted: Boolean) {
    if (isMuted) {
        playEffect(matchEffectId, MATCH_VOLUME / 3)
    } else {
        playEffect(matchEffectId, MATCH_VOLUME)
    }
}

private const val MATCH_VOLUME = 0.7f
private const val FLIP_VOLUME = 0.95f
