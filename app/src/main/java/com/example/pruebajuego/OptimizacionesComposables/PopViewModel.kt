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


    val cantidadCacasTotales = poopData.value.cantidadaAcumuladaCacas
    var diamantesAobtener: Double = cantidadCacasTotales / 1e6

    // REMOVED: Todos los StateFlows individuales (_cacasTotales, _autoSumar, etc.)

    init {
        viewModelScope.launch {

            val loadedData = dataStoreManager.poopDataFlow.first()
            //clearDataStore()



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
                        cacasTotales = _poopData.value.cacasTotales + _poopData.value.cantidadSumar,
                        cantidadaAcumuladaCacas = _poopData.value.cantidadaAcumuladaCacas + _poopData.value.cantidadSumar

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

    fun hacerRebirth(){

        val currentState = _poopData.value
        val newState = currentState.copy(
            diamantesTotales = diamantesAobtener,
            cacasTotales = 0f,
            precioSumClick = 30,
            cacasClick = 1e0,
            cantidadCacaTotales = 0,
            cantidadSumar = 0f,
            autoSumar = false,
            precioYourBath = 20,
            precioInodoros = 1000,
            precioPopClicker = 5e3,
            precioToolsClick = 7e14,
            precioSmartPoop = 6e8,
            precioPublicBath = 2e4,
            precioPopClicker2 = 5e5,
            precioPoopStars = 1e13,
            precioPoopEconomy = 3e9,
            precioPoopCities = 5e7,
            precioMultiPoops = 1e8,
            precioFireFinguer = 4e10,
            precioCleanPoops = 1e16,
            precioBestPopClicker = 5e6,
            precioBestPooper = 1e7,
            precioAscendPoops = 5e18,
            precioAncientPoops = 1e11,
            precioPoopAge = 1e20,
            precioPoopEarht = 5e12,
            precioVertedero = 1e5,
            precioAnimals = 2e6,
            precioCliker = 1e22,
            mostrarClickUpgrade = false,
            mostrarClickUpgrade2 = false,
            mostrarClickUpgrade3 = false,
            mostrarClickUpgrade4 = false,
            mostrarClickUpgrade5 = false,
            mostrarClickUpgrade6 = false,
            mostrarClickUpgrade7 = false,
            mostrarClickUpgrade8 = false,
            mostrarClickUpgrade9 = false,
            mostrarClickUpgrade10 = false,
            mostrarClickUpgrade11 = false,
            mostrarClickUpgrade12 = false,
            mostrarClickUpgrade13 = false,
            mostrarClickUpgrade14 = false,
            mostrarClickUpgrade15 = false,
            mostrarClickUpgrade16 = false,
            mostrarClickUpgrade17 = false,
            mostrarClickUpgrade18 = false,
            mostrarClickUpgrade19 = false,
            mostrarClickUpgrade20 = false,
            mostrarClickUpgrade21 = false,
            mostrarClickUpgrade22 = false,
            mostrarClickUpgrade23 = false,
            mostrarClickUpgrade24 = false,


        )

        _poopData.value = newState

        guardar()

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
        _poopData.value = _poopData.value.copy(cacasTotales = _poopData.value.cacasTotales + currentState.cacasClick.toInt())

        _poopData.value = _poopData.value.copy(cantidadaAcumuladaCacas  = _poopData.value.cantidadaAcumuladaCacas + currentState.cacasClick.toInt())



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
                cacasTotales = currentState.cacasTotales - currentState.precioPopClicker.toInt(),
                precioPopClicker = (currentState.precioPopClicker * 1.15f),
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
                cacasTotales = currentState.cacasTotales - currentState.precioPublicBath.toInt(),
                precioPublicBath = (currentState.precioPublicBath * 1.15f),
                mostrarClickUpgrade5 = true, // Marcar esta mejora como comprada
                cantidadSumar = currentState.cantidadSumar + 5f // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }

    fun mejoraVertedero() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioVertedero) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioVertedero.toInt(),
                precioVertedero = (currentState.precioVertedero * 1.15f),
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
                cacasTotales = currentState.cacasTotales - currentState.precioPopClicker2.toInt(),
                precioPopClicker2 = (currentState.precioPopClicker2 * 1.15f),
                mostrarClickUpgrade7 = true, // Marcar esta mejora como comprada
                cacasClick = currentState.cacasClick + 250 // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }

    fun mejoraAnimalPoop() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioAnimals) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioAnimals.toInt(),
                precioAnimals = (currentState.precioAnimals * 1.15f),
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
                cacasTotales = currentState.cacasTotales - currentState.precioBestPopClicker.toInt(),
                precioBestPopClicker = (currentState.precioBestPopClicker * 1.15f),
                mostrarClickUpgrade9 = true, // Marcar esta mejora como comprada
                cacasClick = currentState.cacasClick + 500 // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }

    fun mejoraBestPooper() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioBestPooper) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioBestPooper.toInt(),
                precioBestPooper = (currentState.precioBestPooper * 1.15f),
                mostrarClickUpgrade10 = true, // Marcar esta mejora como comprada
                cantidadSumar = currentState.cantidadSumar + 500f // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }


    fun mejoraPoopCities() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioPoopCities) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioPoopCities.toInt(),
                precioPoopCities = (currentState.precioPoopCities * 1.15f),
                mostrarClickUpgrade11 = true, // Marcar esta mejora como comprada
                cantidadSumar = currentState.cantidadSumar + 1000f // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }

    fun mejoraMultiPoops() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioMultiPoops) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioMultiPoops.toInt(),
                precioMultiPoops = (currentState.precioMultiPoops * 1.15f),
                mostrarClickUpgrade12 = true, // Marcar esta mejora como comprada
                cantidadSumar = currentState.cantidadSumar + 5000f // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }

    fun mejoraSmartPoop() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioSmartPoop) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioSmartPoop.toInt(),
                precioSmartPoop = (currentState.precioSmartPoop * 1.15f),
                mostrarClickUpgrade13 = true, // Marcar esta mejora como comprada
                cantidadSumar = currentState.cantidadSumar + 20000f // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }


    fun mejoraPoopEconomy() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioPoopEconomy) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioPoopEconomy.toInt(),
                precioPoopEconomy = (currentState.precioPoopEconomy * 1.15f),
                mostrarClickUpgrade14 = true, // Marcar esta mejora como comprada
                cantidadSumar = currentState.cantidadSumar + 100000f // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }



    fun mejoraFireFinguer() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioFireFinguer) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioFireFinguer.toInt(),
                precioFireFinguer = (currentState.precioFireFinguer * 1.15f),
                mostrarClickUpgrade15 = true, // Marcar esta mejora como comprada
                cacasClick = currentState.cacasClick + 1e4 // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }


    fun mejoraAncientPoops() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioAncientPoops) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioAncientPoops.toInt(),
                precioAncientPoops = (currentState.precioAncientPoops * 1.15f),
                mostrarClickUpgrade16 = true, // Marcar esta mejora como comprada
                cantidadSumar = currentState.cantidadSumar + 5000000f // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }

    fun mejoraPoopEarht() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioPoopEarht) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioPoopEarht.toInt(),
                precioPoopEarht = (currentState.precioPoopEarht * 1.15f),
                mostrarClickUpgrade17 = true, // Marcar esta mejora como comprada
                cantidadSumar = currentState.cantidadSumar + 20000000f // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }

    fun mejoraPoopStars() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioPoopStars) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioPoopStars.toInt(),
                precioPoopStars = (currentState.precioPoopStars * 1.15f),
                mostrarClickUpgrade18 = true, // Marcar esta mejora como comprada
                cantidadSumar = currentState.cantidadSumar + 100000000f // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }

    fun mejoraToolsClick() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioToolsClick) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioToolsClick.toInt(),
                precioToolsClick = (currentState.precioToolsClick * 1.15f),
                mostrarClickUpgrade19 = true, // Marcar esta mejora como comprada
                cacasClick = currentState.cacasClick + 1e5 // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }

    fun mejoraCleanPoops() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioCleanPoops) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioCleanPoops.toInt(),
                precioCleanPoops = (currentState.precioCleanPoops * 1.15f),
                mostrarClickUpgrade20 = true, // Marcar esta mejora como comprada
                cantidadSumar = currentState.cantidadSumar + 5000000000f // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }

    fun mejoraAscendPoops() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioAscendPoops) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioAscendPoops.toInt(),
                precioAscendPoops = (currentState.precioAscendPoops * 1.15f),
                mostrarClickUpgrade21 = true, // Marcar esta mejora como comprada
                cantidadSumar = currentState.cantidadSumar + 100000000000f // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }

    fun mejoraPoopAge() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioPoopAge) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioPoopAge.toInt(),
                precioPoopAge = (currentState.precioPoopAge * 1.15f),
                mostrarClickUpgrade22 = true, // Marcar esta mejora como comprada
                cantidadSumar = currentState.cantidadSumar + 20000000000000f // <-- Esto añade la cantidad

            )

            _poopData.value = newState
            guardar()
        }
    }

    fun mejoraCliker() {
        val currentState = _poopData.value
        if (currentState.cacasTotales >= currentState.precioCliker) {
            val newState = currentState.copy(
                cacasTotales = currentState.cacasTotales - currentState.precioCliker.toInt(),
                precioCliker = (currentState.precioCliker * 1.15f),
                mostrarClickUpgrade23 = true, // Marcar esta mejora como comprada
                cacasClick = currentState.cacasClick + 1e9 // <-- Esto añade la cantidad

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