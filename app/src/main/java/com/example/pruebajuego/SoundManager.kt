package com.example.pruebajuego

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.util.Log // Agregamos Log para mensajes de depuración si es necesario

class SoundManager(context: Context) {
    // private val audioManager: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager // Puedes comentar o eliminar si no la usas

    private var currentEffectsVolume: Float = 0.7f // Estado interno del volumen de efectos

    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(5)
        .setAudioAttributes(
            AudioAttributes.Builder()
                // *** CAMBIA ESTE USAGE ***
                .setUsage(AudioAttributes.USAGE_GAME) // Prueba USAGE_GAME o USAGE_MEDIA
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION) // O CONTENT_TYPE_SONIFICATION, CONTENT_TYPE_SPEECH, etc.
                .build()
        ).build()

    private var pedoSoundId: Int = 0
    private var buySoundId: Int = 0
    // ... (otros IDs y variables para cargar sonidos) ...
    private var soundsLoaded = false
    private val soundsToLoadCount = 2 // Ajusta según el número de efectos cargados
    private val loadedSoundMap = mutableMapOf<Int, Boolean>()


    init {
        soundPool.setOnLoadCompleteListener { soundPool, sampleId, status ->
            if (status == 0) {
                loadedSoundMap[sampleId] = true
                Log.d("SoundManager", "Sound loaded: $sampleId")
                if (loadedSoundMap.size == soundsToLoadCount && loadedSoundMap.all { it.value }) {
                    soundsLoaded = true
                    Log.d("SoundManager", "All sounds loaded successfully.")
                }
            } else {
                Log.e("SoundManager", "Error loading sound: $sampleId with status $status")
            }
        }

        try {
            pedoSoundId = soundPool.load(context, R.raw.pedoduro, 1)
            loadedSoundMap[pedoSoundId] = false
            buySoundId = soundPool.load(context, R.raw.comprar, 1)
            loadedSoundMap[buySoundId] = false

            Log.d("SoundManager", "Initiated sound loading. Pedo ID: $pedoSoundId, Buy ID: $buySoundId")
        } catch (e: Exception) {
            Log.e("SoundManager", "Error initiating sound loading:", e)
        }
    }

    fun setEffectsVolume(volume: Float) {
        currentEffectsVolume = volume.coerceIn(0f, 1f)
        Log.d("SoundManager", "Effects volume set to: $currentEffectsVolume")
        // El volumen se aplica en play()
    }

    fun playPedoSound() {
        if (soundsLoaded && pedoSoundId != 0 && currentEffectsVolume >= 0f) { // Permitir volumen 0f pero no reproducir
            if (currentEffectsVolume > 0f) { // Solo reproducir si el volumen es mayor que 0
                val streamId = soundPool.play(pedoSoundId, currentEffectsVolume, currentEffectsVolume, 0, 0, 1f)
                if (streamId == 0) {
                    Log.e("SoundManager", "SoundPool failed to play pedo sound. Stream ID is 0.")
                } else {
                    Log.d("SoundManager", "Playing pedo sound (Stream ID $streamId) with volume: $currentEffectsVolume")
                }
            } else {
                Log.d("SoundManager", "Effects volume is zero. Not playing pedo sound.")
            }
        } else {
            if (!soundsLoaded) Log.w("SoundManager", "SoundPool sounds not loaded yet. Cannot play pedo.")
            if (pedoSoundId == 0) Log.w("SoundManager", "Pedo sound ID is 0, possibly failed to load. Cannot play.")
            // El caso volume <= 0 ya se maneja arriba
        }
    }

    fun playBuySound() {
        if (soundsLoaded && buySoundId != 0 && currentEffectsVolume >= 0f) { // Permitir volumen 0f pero no reproducir
            if (currentEffectsVolume > 0f) { // Solo reproducir si el volumen es mayor que 0
                val streamId = soundPool.play(buySoundId, currentEffectsVolume, currentEffectsVolume, 0, 0, 1f)
                if (streamId == 0) {
                    Log.e("SoundManager", "SoundPool failed to play buy sound. Stream ID is 0.")
                } else {
                    Log.d("SoundManager", "Playing buy sound (Stream ID $streamId) with volume: $currentEffectsVolume")
                }
            } else {
                Log.d("SoundManager", "Effects volume is zero. Not playing buy sound.")
            }
        } else {
            if (!soundsLoaded) Log.w("SoundManager", "SoundPool sounds not loaded yet. Cannot play buy.")
            if (buySoundId == 0) Log.w("SoundManager", "Buy sound ID is 0, possibly failed to load. Cannot play.")
            // El caso volume <= 0 ya se maneja arriba
        }
    }


    fun release() {
        soundPool.release()
        // ... (liberar otros recursos si los hay) ...
        Log.d("SoundManager", "SoundPool released.")
    }

    // La función isMediaVolumeOn() y playSoundIfVolumeOn() no se usan en play...Sound, puedes quitarlas si no las necesitas en otro lado.
}