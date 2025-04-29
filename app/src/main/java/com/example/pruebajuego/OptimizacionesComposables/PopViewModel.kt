package com.example.pruebajuego.OptimizacionesComposables

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebajuego.dataStore.DataStoreManager
import com.example.pruebajuego.dataStore.PoopData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first

import kotlinx.coroutines.launch


class PoopViewModel(context: Context) : ViewModel() {

    private val dataStoreManager = DataStoreManager(context = context)

    // *** ÚNICA fuente de verdad para todo el estado ***
    private val _poopData = MutableStateFlow(PoopData())
    val poopData: StateFlow<PoopData> = _poopData.asStateFlow() // Exponemos como read-only

    // REMOVED: Todos los StateFlows individuales (_cacasTotales, _autoSumar, etc.)

    init {
        viewModelScope.launch {

            val loadedData = dataStoreManager.poopDataFlow.first()
            clearDataStore()



            _poopData.value = loadedData.takeIf { it.cacasTotales != 0f || it.cantidadCacaTotales != 0 } ?: PoopData()

            startAutoSumar()

            // Opcional: Si necesitas observar cambios en DataStore *después* de la carga inicial
            // por alguna razón (menos común), puedes seguir colectando, pero first()
            // es suficiente para la carga inicial.
            dataStoreManager.poopDataFlow.collect { newData ->
                // Esto solo se ejecutaría si DataStore cambia *fuera* de este ViewModel,
                // lo cual no debería pasar si solo guardas desde aquí.
                // Pero si lo usas, actualiza _poopData.value con newData
                // _poopData.value = newData
            }
        }
    }


    private fun startAutoSumar() {
        viewModelScope.launch {
            var saveCounter = 0 // Contador para saber cuántos "ticks" han pasado desde el último guardado
            val saveIntervalTicks = 100 // Queremos guardar cada 10 segundos (100 ticks * 100ms/tick = 10000ms = 10s)

            while (true) {
                // Verificamos si el autoSumar está activo y hay algo que sumar
                if (_poopData.value.autoSumar && _poopData.value.cantidadSumar > 0) {
                    // Actualizamos el estado en memoria (el StateFlow)
                    _poopData.value = _poopData.value.copy(
                        cacasTotales = _poopData.value.cacasTotales + _poopData.value.cantidadSumar
                    )

                    // Incrementamos el contador de ticks para el guardado
                    saveCounter++

                    // Si el contador alcanza el intervalo deseado, guardamos y reseteamos el contador
                    if (saveCounter >= saveIntervalTicks) {
                        guardar() // Guardar en DataStore (esto se ejecuta en Dispatchers.IO)
                        saveCounter = 0 // Resetear el contador
                    }

                } else {
                    // Si el autoSumar NO está activo, reseteamos el contador de guardado.
                    // Esto asegura que cuando el autoSumar se active de nuevo, el guardado
                    // ocurra relativamente pronto (dentro de los primeros 10 segundos)
                    // y no espere a completar un ciclo basado en un contador viejo.
                    saveCounter = 0
                }

                // Esperar 100ms antes de la siguiente iteración
                delay(100L)
            }
        }
    }


    // Función interna para guardar el estado actual de _poopData.value
    private fun guardar() {
        // Usamos Dispatchers.IO porque las operaciones de DataStore son I/O
        viewModelScope.launch(Dispatchers.IO) {
            val dataToSave = _poopData.value
            Log.d("PoopViewModel", "Intentando guardar PoopData: $dataToSave")
            dataStoreManager.guardarPoopData(dataToSave)
            Log.d("PoopViewModel", "Guardado completado")
        }
    }

    fun clickCaca() {
        val currentState = _poopData.value
        // Actualizamos el estado copiando el PoopData actual y modificando cacasTotales
        _poopData.value = _poopData.value.copy(cacasTotales = _poopData.value.cacasTotales + currentState.cacasClick)
        guardar() // Guardamos en DataStore
    }

    fun comprarMejoraYourBath() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioYourBath) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioYourBath,
                precioYourBath = (currentState.precioYourBath * 1.15f).toInt(),
                mostrarClickUpgrade = true, // Marcar esta mejora como comprada
                cantidadSumar = currentState.cantidadSumar + 0.1f // <-- Esto añade la cantidad
                ,autoSumar = true
            )

            _poopData.value = newState
            guardar()
        }
    }

    // Ejemplo de cómo otras funciones deberían actualizar el estado:

    fun comprarMejorClick() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioSumClick) {

            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioSumClick,
                precioSumClick = (currentState.precioSumClick * 1.15f).toInt(),
                cacasClick = currentState.cacasClick + 1, // Marcar esta mejora como comprada
                mostrarClickUpgrade2 = true // <-- Esto añade la cantidad
                ,autoSumar = true
            )

            _poopData.value = newState
            guardar()

        }
    }

    fun comprarMejoraInodoros() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioInodoros) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioInodoros,
                precioInodoros = (currentState.precioInodoros * 1.15f).toInt(),
                mostrarClickUpgrade3 = true, // Marcar esta mejora como comprada
                cantidadSumar = currentState.cantidadSumar + 1f // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }

    fun mejoraPoopClick() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioPopClicker) {
            _poopData.value = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioPopClicker,
                precioPopClicker = (currentState.precioPopClicker * 1.15f).toInt(),
                cacasClick = currentState.cacasClick + 5, // Incrementa la cantidad sumada
                mostrarClickUpgrade4 = true

            )

            guardar()
        }
    }

    fun publicBathrooms() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioPublicBath) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioPublicBath,
                precioPublicBath = (currentState.precioPublicBath * 1.15f).toInt(),
                mostrarClickUpgrade5 = true, // Marcar esta mejora como comprada
                cantidadSumar = currentState.cantidadSumar + 50f // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }

    fun mejoraVertedero() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioVertedero) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioVertedero,
                precioVertedero = (currentState.precioVertedero * 1.15f).toInt(),
                mostrarClickUpgrade6 = true, // Marcar esta mejora como comprada
                cantidadSumar = currentState.cantidadSumar + 25f // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }

    fun mejoratheBestTapPoop() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioPopClicker2) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioPopClicker2,
                precioPopClicker2 = (currentState.precioPopClicker2 * 1.15f).toInt(),
                mostrarClickUpgrade7 = true, // Marcar esta mejora como comprada
                cacasClick = currentState.cacasClick + 50 // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }

    fun mejoraAnimalPoop() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioAnimals) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioAnimals,
                precioAnimals = (currentState.precioAnimals * 1.15f).toInt(),
                mostrarClickUpgrade8 = true, // Marcar esta mejora como comprada
                cantidadSumar = currentState.cantidadSumar + 250f // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }

    fun mejoraPoopFinger() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioBestPopClicker) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioBestPopClicker,
                precioBestPopClicker = (currentState.precioBestPopClicker * 1.15f).toInt(),
                mostrarClickUpgrade8 = true, // Marcar esta mejora como comprada
                cacasClick = currentState.cacasClick + 500 // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }












    fun actualizarCantidadTotalCacas() {
        // Esta función parece querer actualizar cantidadCacaTotales con el valor actual de cacasTotales
        // Solo actualizamos si la cantidad total es menor (ej: para un contador de total histórico)
        val currentState = _poopData.value
        if (currentState.cantidadCacaTotales < currentState.cacasTotales.toInt()) {
            _poopData.value = currentState.copy(
                cantidadCacaTotales = currentState.cacasTotales.toInt()
            )
            guardar()
        }
    }


    // Elimina clearDataStore de aquí si no la necesitas en el ViewModel.
    // Si la necesitas, mantenla pero úsala con cuidado.

    suspend fun clearDataStore() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.clearDataStore()
            // Opcional: Resetear el estado del ViewModel a los valores por defecto después de borrar DataStore
            _poopData.value = PoopData()
        }
    }


    // Puedes agregar más funciones de upgrades aquí, siguiendo el patrón de
    // leer _poopData.value, crear un nuevo estado con copy(), y asignar a _poopData.value
}