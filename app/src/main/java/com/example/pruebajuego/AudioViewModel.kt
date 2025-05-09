package com.example.pruebajuego // Asegúrate de que este sea tu paquete

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider // Importar ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras // Importar CreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class AudioViewModel(private val context: Context) : ViewModel() { // El constructor ahora recibe Context
    private var mediaPlayer: MediaPlayer? = null

    // TODO: Reemplaza R.raw.cancionfondo con el ID de tu recurso de audio
    // Asegúrate de que tienes un archivo de audio llamado cancionfondo en res/raw/
    private val audioResourceId: Int = R.raw.cancionfondo // *** VERIFICA ESTO ***
    private val TAG = "AudioViewModel" // Etiqueta para Logcat


    // 1. Estado para el volumen de la música
    // Usamos StateFlow para que el Composable pueda observarlo
    private val _musicVolume = MutableStateFlow(0.5f) // Valor inicial del volumen (0.5f es medio)
    val musicVolume: StateFlow<Float> = _musicVolume.asStateFlow()

    init {
        // El bloque init se ejecuta cuando se crea la instancia del ViewModel
        Log.d(TAG, "AudioViewModel creado con Context")
        // La inicialización del MediaPlayer se hará en startBackgroundMusic()
    }


    fun startBackgroundMusic() { // Ya no necesita Context aquí, lo tiene en el constructor
        // Log al inicio de la función
        Log.d(TAG, "-> startBackgroundMusic llamado. isPlaying actual: ${mediaPlayer?.isPlaying}")

        if (mediaPlayer == null) {
            Log.d(TAG, "   startBackgroundMusic: Creando nuevo MediaPlayer.")
            try {
                // Crear el MediaPlayer usando el contexto del ViewModel
                mediaPlayer = MediaPlayer.create(context, audioResourceId).apply { // Usa el contexto del constructor
                    // Puedes añadir listeners para ver estados intermedios (opcional pero útil)
                    setOnPreparedListener { Log.d(TAG, "   MediaPlayer: Preparado") }
                    setOnErrorListener { mp, what, extra ->
                        Log.e(TAG, "   MediaPlayer Error: what=$what, extra=$extra")
                        // TODO: Manejar el error (ej: mostrar un mensaje, detener reproducción)
                        // stopBackgroundMusic() // Detener en caso de error
                        false // Indica que el error fue manejado
                    }

                    isLooping = true
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    // Aplicar el volumen inicial
                    setVolume(_musicVolume.value, _musicVolume.value)

                    // Normalmente, con create() para raw resources, se puede llamar a start() aquí.
                    // Si usas prepareAsync() y setOnPreparedListener, llamarías start() en el listener.
                    try {
                        start()
                        Log.d(TAG, "   startBackgroundMusic: start() llamado después de crear.")
                    } catch (e: Exception) {
                        Log.e(TAG, "   startBackgroundMusic: Error al llamar start() después de crear: ${e.message}")
                    }

                }
            } catch (e: Exception) {
                Log.e(TAG, "Error al crear MediaPlayer: ${e.message}")
                // TODO: Manejar el error de creación
                // stopPlayback() // Detener si no se pudo crear
                return // Salir de la función
            }
        } else if (mediaPlayer?.isPlaying == false) {
            Log.d(TAG, "   startBackgroundMusic: MediaPlayer existe y NO está sonando. Llamando a start().")
            // Aquí aplicamos el volumen por si cambió mientras estaba pausado/detenido
            mediaPlayer?.setVolume(_musicVolume.value, _musicVolume.value)
            try {
                mediaPlayer!!.start()
                Log.d(TAG, "   startBackgroundMusic: start() llamado en reproductor existente.")
            } catch (e: Exception) {
                Log.e(TAG, "   startBackgroundMusic: Error al llamar start() en reproductor existente: ${e.message}")
            }
        } else {
            Log.d(TAG, "   startBackgroundMusic: MediaPlayer ya existe y SÍ está sonando. No se hace nada.")
        }
        Log.d(TAG, "<- startBackgroundMusic finalizado.")
    }

    // 2. Función para detener la música si es necesario (opcional, pero útil)
    fun stopBackgroundMusic() {
        Log.d(TAG, "stopBackgroundMusic llamado.")
        mediaPlayer?.stop()
        // No liberar aquí, liberar en onCleared()
        // mediaPlayer?.release()
        // mediaPlayer = null
    }

    // 3. Función para pausar la música (opcional, pero útil)
    fun pauseBackgroundMusic() {
        Log.d(TAG, "pauseBackgroundMusic llamado.")
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
        Log.d(TAG, "onCleared: ViewModel destruido. Liberando MediaPlayer.")
        mediaPlayer?.release() // Libera los recursos del MediaPlayer
        mediaPlayer = null
    }

    // *** CLASE FACTORY ANIDADA PARA CREAR EL VIEWMODEL CON PARÁMETROS ***
    // Esta clase es lo que necesitas para pasar el 'Context' al constructor de AudioViewModel
    class Factory(private val context: Context) : ViewModelProvider.Factory {

        // Sobrescribe el método create para proporcionar la instancia de tu ViewModel
        @Suppress("UNCHECKED_CAST") // Se necesita esta anotación por el tipo genérico T
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            // Verifica si la clase del ViewModel solicitada es AudioViewModel
            if (modelClass.isAssignableFrom(AudioViewModel::class.java)) {
                // Si es AudioViewModel, crea una nueva instancia pasándole el contexto
                return AudioViewModel(context) as T
            }
            // Si se solicita otro tipo de ViewModel, lanza una excepción
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
