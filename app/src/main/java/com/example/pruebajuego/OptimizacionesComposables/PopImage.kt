package com.example.pruebajuego.OptimizacionesComposables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pruebajuego.AudioViewModel
import com.example.pruebajuego.R
import com.example.pruebajuego.SoundManager

@Composable
fun CacaImage(
    cantidadTotalCacas: Int,
    scale: Float,
    soundManager: SoundManager,
    onClickAction: () -> Unit
) {




    val imageId = remember(cantidadTotalCacas) {
        when {
            cantidadTotalCacas <= 1e3 -> R.drawable.caca1removebg
            cantidadTotalCacas <= 1e4 -> R.drawable.caca2rmbc
            cantidadTotalCacas <= 100000 -> R.drawable.caca3removebg
            cantidadTotalCacas <= 1e6 -> R.drawable.caca4removebg
            cantidadTotalCacas <= 5e6 -> R.drawable.caca5removebg
            cantidadTotalCacas <= 1e7 -> R.drawable.caca6removebg
            cantidadTotalCacas <= 1e8 -> R.drawable.caca7removebg
            else -> R.drawable.caca8removebg
        }
    }

    val painter = painterResource(id = imageId)

    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.FillHeight,
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale
            )
            .clickable(
                interactionSource = null,
                indication = null
            ) {
                soundManager.playPedoSound() // 👉 aquí reproducimos el sonido
                onClickAction()               // 👉 y luego lo que tú quieras que pase
            }
    )
}