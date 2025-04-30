package com.example.pruebajuego.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// Crea el DataStore
val Context.dataStore by preferencesDataStore(name = "poop_preferences")

class DataStoreManager(private val context: Context) {


    companion object {
        val POOP_DATA_KEY = stringPreferencesKey("poop_data")
    }

    // Aquí usamos un Flow para recuperar el PoopData desde DataStore
    val poopDataFlow: Flow<PoopData> = context.dataStore.data
        .map { preferences ->
            preferences[POOP_DATA_KEY]?.let { jsonString ->
                Json.decodeFromString<PoopData>(jsonString)
            } ?: PoopData()  // Si no hay datos guardados, devolvemos PoopData vacío
        }

    suspend fun guardarPoopData(poopData: PoopData) {
        context.dataStore.edit { preferences ->
            // Guardamos el objeto PoopData serializado en JSON
            preferences[POOP_DATA_KEY] = Json.encodeToString(poopData)
        }
    }
    suspend fun clearDataStore() {
        context.dataStore.edit { preferences ->
            preferences.clear()  // Borra todo el contenido del DataStore
        }
    }
}