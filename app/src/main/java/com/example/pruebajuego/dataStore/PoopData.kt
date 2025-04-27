package com.example.pruebajuego.dataStore

import kotlinx.serialization.Serializable

@Serializable
data class PoopData(
    val cacasTotales: Float = 0f,
    val cacasClick: Int = 1000000,
    val cantidadCacaTotales: Int = 0,
    val cantidadSumar: Float = 0f,
    val autoSumar: Boolean = false,
    val precioYourBath: Int = 20,
    val precioSumClick: Int = 30,
    val precioInodoros: Int = 1000,
    val precioPopClicker: Int = 5000,
    val precioPublicBath: Int = 20000,
    val precioVertedero: Int = 100000,
    val precioPopClicker2: Int = 500000,
    val precioAnimals: Int = 2000000,
    val precioBestPopClicker: Int = 5000000,
    val mostrarClickUpgrade: Boolean = false,
    val mostrarClickUpgrade2: Boolean = false,
    val mostrarClickUpgrade3: Boolean = false,
    val mostrarClickUpgrade4: Boolean = false,
    val mostrarClickUpgrade5: Boolean = false,
    val mostrarClickUpgrade6: Boolean = false,
    val mostrarClickUpgrade7: Boolean = false,
    val mostrarClickUpgrade8: Boolean = false
)