package com.example.pruebajuego

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import com.example.pruebajuego.R

class SoundManager(context: Context) {
    private val audioManager: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(5)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        ).build()

    val pedoSoundId: Int = soundPool.load(context, R.raw.pedoduro, 1)

    // Verificar si el volumen de medios está activado
    private fun isMediaVolumeOn(): Boolean {
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) > 0
    }

    // Reproducir sonido si el volumen está activado
    fun playPedoSound() {
        if (isMediaVolumeOn()) {
            soundPool.play(pedoSoundId, 1f, 1f, 0, 0, 1f)
        }
    }

    fun release() {
        soundPool.release()
    }
}