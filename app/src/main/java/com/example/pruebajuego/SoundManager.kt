package com.example.pruebajuego

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.example.pruebajuego.R

class SoundManager(context: Context) {
    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(5)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        ).build()

    val pedoSoundId: Int = soundPool.load(context, R.raw.pedoduro, 1)

    fun playPedoSound() {
        soundPool.play(pedoSoundId, 1f, 1f, 0, 0, 1f)
    }

    fun release() {
        soundPool.release()
    }
}