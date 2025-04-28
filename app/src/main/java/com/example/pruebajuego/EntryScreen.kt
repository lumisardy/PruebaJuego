package com.example.pruebajuego

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pruebajuego.ui.theme.RetroFont
import com.example.pruebajuego.ui.theme.marron
import kotlinx.coroutines.delay


@Composable
fun EntryScreen (StartGame:() -> Unit){

    val infiniteTransition = rememberInfiniteTransition(label = "tituloAnim")

    var progress by remember { mutableStateOf(0f) }


    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenWidthDp = configuration.screenWidthDp
    val barraWidthFraction = 0.7f
    val barraWidthDp = screenWidthDp * barraWidthFraction

    val imageOffsetPx = with(density) {
        (progress.coerceIn(0f, 1f) * barraWidthDp.dp.toPx()) - (22.dp.toPx()) // centrado
    }


    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // Animación de rotación oscilante
    val rotation by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )

    val rotation2 by infiniteTransition.animateFloat(
        initialValue = -20f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )

    // Animar la barra de progreso en 4 segundos
    LaunchedEffect(Unit) {
        val duration = 4000L
        val steps = 100
        val delayPerStep = duration / steps

        repeat(steps) {
            progress += 1f / steps
            delay(delayPerStep)
        }

        // Navegar a la pantalla principal
        StartGame()

    }

    // UI de la pantalla splash
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        Image(

            painter = painterResource(id = R.drawable.fondoportada), // Reemplaza con tu imagen
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop)

        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {

            Image(painter = painterResource(id = R.drawable.titulopoopclicker),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .fillMaxWidth(0.8f)
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        rotationZ = rotation
                    ),
                contentScale = ContentScale.FillBounds
            )


            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(barraWidthFraction)
                        .height(94.dp)
                ) {
                    // Barra de progreso
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterStart)
                            .height(15.dp),
                        color = marron,
                        trackColor = Color.LightGray
                    )

                    // Imagen que se superpone al final del progreso
                    Image(
                        painter = painterResource(id = R.drawable.caca1removebg),
                        contentDescription = null,
                        modifier = Modifier
                            .size(94.dp)
                            .graphicsLayer {
                                translationX = imageOffsetPx - 40
                                rotationZ = rotation2

                            }
                            .align(Alignment.CenterStart)
                    )
                }



            }

        }

    }

}