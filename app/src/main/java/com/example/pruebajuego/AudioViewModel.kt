package com.example.pruebajuego



import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class AudioViewModel : ViewModel() {
    private var mediaPlayer: MediaPlayer? = null

    // 1. Estado para el volumen de la música
    // Usamos StateFlow para que el Composable pueda observarlo
    private val _musicVolume = MutableStateFlow(0.5f) // Valor inicial del volumen (0.5f es medio)
    val musicVolume: StateFlow<Float> = _musicVolume.asStateFlow()


    fun startBackgroundMusic(context: Context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.cancionfondo).apply {
                isLooping = true
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                // Aplicar el volumen inicial al crear el MediaPlayer
                setVolume(_musicVolume.value, _musicVolume.value)
                start()
            }
        } else if (!mediaPlayer!!.isPlaying) {
            // Si el reproductor ya existe pero no está sonando (pausado, detenido), lo reinicia
            // Asegúrate de que tenga el volumen actual si se reanuda después de una pausa, aunque start() suele mantenerlo.
            mediaPlayer?.setVolume(_musicVolume.value, _musicVolume.value) // Re-aplicar volumen por si acaso
            mediaPlayer?.start()
        }
    }

    // 2. Función para detener la música si es necesario (opcional, pero útil)
    fun stopBackgroundMusic() {
        mediaPlayer?.stop()
    }

    // 3. Función para pausar la música (opcional, pero útil)
    fun pauseBackgroundMusic() {
        mediaPlayer?.pause()
    }


    // 4. Función para cambiar el volumen de la música
    fun setMusicVolume(volume: Float) {
        // Asegurarse de que el volumen esté entre 0.0f y 1.0f
        val clampedVolume = volume.coerceIn(0f, 1f)

        // Actualizar el estado del volumen
        _musicVolume.value = clampedVolume

        // Aplicar el volumen al MediaPlayer si existe
        mediaPlayer?.setVolume(clampedVolume, clampedVolume)

        // Opcional: si quieres que los cambios de volumen se guarden entre sesiones
        // podrías guardar clampedVolume en SharedPreferences aquí.
    }


    // Se llama cuando el ViewModel ya no es necesario y va a ser destruido
    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release() // Libera los recursos del MediaPlayer
        mediaPlayer = null
    }
}