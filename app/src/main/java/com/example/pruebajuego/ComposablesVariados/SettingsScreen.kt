package com.example.pruebajuego.ComposablesVariados

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.pruebajuego.R

@Composable
fun GameSettingsScreen() {
    // Estado para controlar si el diálogo está abierto o cerrado
    var showSettingsDialog by remember { mutableStateOf(false) }

    // Estados para los valores de los sliders (ejemplo)
    var musicVolume by remember { mutableStateOf(0.5f) }
    var effectsVolume by remember { mutableStateOf(0.7f) }

    Box(modifier = Modifier.fillMaxSize(0.6f)) {
        // Tu imagen con el modificador clickable
        Image(
            painter = painterResource(id = R.drawable.cartel), // Reemplaza R.drawable.cartel con tu recurso de imagen
            contentScale = ContentScale.FillBounds,
            contentDescription = "Icono de Ajustes", // Agrega una descripción accesible
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight()
                .align(Alignment.Center) // Alinea la imagen al centro, ajusta si es necesario
                .clickable {
                    showSettingsDialog = true // Abre el diálogo al hacer click
                }
        )

        // El Diálogo de Ajustes
        if (showSettingsDialog) {
            Dialog(onDismissRequest = { showSettingsDialog = false }) {
                Card { // Usamos Card para darle un aspecto de tarjeta al diálogo
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text("Ajustes")

                        // Slider para la Música
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Música:")
                            Slider(
                                value = musicVolume,
                                onValueChange = { musicVolume = it },
                                valueRange = 0f..1f,
                                modifier = Modifier.weight(1f)
                            )
                            Text((musicVolume * 100).toInt().toString()) // Muestra el porcentaje
                        }

                        // Slider para los Efectos
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Efectos:")
                            Slider(
                                value = effectsVolume,
                                onValueChange = { effectsVolume = it },
                                valueRange = 0f..1f,
                                modifier = Modifier.weight(1f)
                            )
                            Text((effectsVolume * 100).toInt().toString()) // Muestra el porcentaje
                        }

                        // Botón para cerrar el diálogo
                        Button(onClick = { showSettingsDialog = false }) {
                            Text("Cerrar")
                        }
                    }
                }
            }
        }
    }
}