package com.example.pruebajuego

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pruebajuego.ui.theme.NegroTrans
import com.example.pruebajuego.ui.theme.PopsFont
import com.example.pruebajuego.ui.theme.marron
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(){


    var CacasTotales by remember { mutableStateOf(0) }
    var autoSumar by remember { mutableStateOf(false) }
    var CatidadSumar by remember { mutableStateOf(0) }
    var CantidadTotalCacas by remember { mutableStateOf(0) }





    var PrecioBabyPop by remember { mutableStateOf(20) }
    var PrecioSumClick by remember { mutableStateOf(30) }
    var PrecioBigPop by remember { mutableStateOf(1000) }



    var CacasClick by remember { mutableStateOf(1) }



    var mostrarClickUpgrade by remember { mutableStateOf(false) }
    var mostrarClickUpgrade2 by remember { mutableStateOf(false) }
    var mostrarClickUpgrade3 by remember { mutableStateOf(false) }
    var mostrarClickUpgrade4 by remember { mutableStateOf(false) }
    var mostrarClickUpgrade5 by remember { mutableStateOf(false) }
    var mostrarClickUpgrade6 by remember { mutableStateOf(false) }
    var mostrarClickUpgrade7 by remember { mutableStateOf(false) }
    var mostrarClickUpgrade8 by remember { mutableStateOf(false) }




    var isPressed by remember { mutableStateOf(false) }

    // Animamos el valor de escala
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.85f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "scaleAnim"
    )


    if (CantidadTotalCacas < CacasTotales){

        CantidadTotalCacas = CacasTotales

    }


    LaunchedEffect(autoSumar) {
        while (autoSumar) {
            delay(1000L)
            CacasTotales += CatidadSumar
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo), // Reemplaza con tu imagen
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start)  {

            Box(Modifier.fillMaxWidth().fillMaxHeight(0.1f), contentAlignment = Alignment.BottomCenter){


                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text("Pops: $CacasTotales", fontFamily = PopsFont , fontSize = 30.sp, letterSpacing = 3.sp)
                    Text("$CatidadSumar /s",fontFamily = PopsFont, fontSize = 18.sp)

                }

            }


            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {


                Box(modifier = Modifier.weight(1f).padding(20.dp), contentAlignment = Alignment.Center){

                    Image(
                    painter = if (CantidadTotalCacas <= 1e3){ painterResource(id = R.drawable.caca1removebg)} else if (CantidadTotalCacas <= 1e4) {painterResource(id = R.drawable.caca2rmbc)} else if (CantidadTotalCacas <= 100000) {painterResource(id = R.drawable.caca3removebg)} else if (CantidadTotalCacas <= 1e6){painterResource(id = R.drawable.caca4removebg)} else if(CantidadTotalCacas <= 5e6) {painterResource(id = R.drawable.caca5removebg)} else if (CantidadTotalCacas <= 1e7){painterResource(id = R.drawable.caca6removebg)} else if (CantidadTotalCacas <= 1e8){painterResource(id = R.drawable.caca7removebg)} else {painterResource(id = R.drawable.caca8removebg)},
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale
                        )
                        .clickable(interactionSource = null,
                            indication = null) {

                            isPressed = true
                            CacasTotales += CacasClick
                            // Volver al tamaño normal después de un pequeño delay
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(100L)
                                isPressed = false
                            }
                        }
                )}



                Card(modifier = Modifier.weight(1f).fillMaxHeight(0.7f)) {

                    Column(Modifier.fillMaxSize().background(NegroTrans).verticalScroll(rememberScrollState()).padding(10.dp)) {

                        Box(){



                            Column {
                                Text("Baby Pop", fontWeight = FontWeight.Bold, color = Color.White)

                                Button(onClick = {
                                    if (CacasTotales >= PrecioBabyPop) {
                                        CacasTotales -= PrecioBabyPop
                                        autoSumar = true
                                        CatidadSumar += 1
                                        mostrarClickUpgrade = true
                                        PrecioBabyPop = (PrecioBabyPop * 1.2).toInt()
                                    }
                                }, shape = RoundedCornerShape(5.dp), border = BorderStroke(2.dp, Color.Black), colors = ButtonDefaults.buttonColors(containerColor = marron)) {
                                    Text("1/s Cost: $PrecioBabyPop", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                }
                            }


                        }

                        Spacer(Modifier.height(10.dp))

                        if (mostrarClickUpgrade) {
                            Spacer(Modifier.height(10.dp))

                            Box {
                                Column {
                                    Text("+1 por click", fontWeight = FontWeight.Bold, color = Color.White)

                                    Button(
                                        onClick = {
                                            if (CacasTotales >= PrecioSumClick) {
                                                CacasTotales -= PrecioSumClick
                                                mostrarClickUpgrade2 = true
                                                PrecioSumClick = (PrecioSumClick * 1.5).toInt()
                                                CacasClick += 1
                                            }
                                        },
                                        shape = RoundedCornerShape(5.dp),
                                        border = BorderStroke(2.dp, Color.Black),
                                        colors = ButtonDefaults.buttonColors(containerColor = marron)
                                    ) {
                                        Text("1/s Cost: $PrecioSumClick", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(10.dp))

                        if (mostrarClickUpgrade2) {
                            Spacer(Modifier.height(10.dp))

                            Box {
                                Column {
                                    Text("+Big pop", fontWeight = FontWeight.Bold, color = Color.White)

                                    Button(
                                        onClick = {
                                            if (CacasTotales >= PrecioBigPop) {
                                                CacasTotales -= PrecioBigPop
                                                mostrarClickUpgrade3 = true
                                                PrecioBigPop = (PrecioBigPop * 1.5).toInt()
                                                CatidadSumar += 10
                                            }
                                        },
                                        shape = RoundedCornerShape(5.dp),
                                        border = BorderStroke(2.dp, Color.Black),
                                        colors = ButtonDefaults.buttonColors(containerColor = marron)
                                    ) {
                                        Text("10/s Cost: $PrecioBigPop", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(10.dp))

                        if (mostrarClickUpgrade3) {
                            Spacer(Modifier.height(10.dp))

                            Box {
                                Column {
                                    Text("+1 por click", fontWeight = FontWeight.Bold, color = Color.White)

                                    Button(
                                        onClick = {
                                            if (CacasTotales >= PrecioSumClick) {
                                                CacasTotales -= PrecioSumClick
                                                mostrarClickUpgrade4 = true
                                                PrecioSumClick = (PrecioSumClick * 1.5).toInt()
                                                CacasClick += 1
                                            }
                                        },
                                        shape = RoundedCornerShape(5.dp),
                                        border = BorderStroke(2.dp, Color.Black),
                                        colors = ButtonDefaults.buttonColors(containerColor = marron)
                                    ) {
                                        Text("1/s Cost: $PrecioSumClick", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(10.dp))
                        if (mostrarClickUpgrade4) {
                            Spacer(Modifier.height(10.dp))

                            Box {
                                Column {
                                    Text("+1 por click", fontWeight = FontWeight.Bold, color = Color.White)

                                    Button(
                                        onClick = {
                                            if (CacasTotales >= PrecioSumClick) {
                                                CacasTotales -= PrecioSumClick
                                                mostrarClickUpgrade5 = true
                                                PrecioSumClick = (PrecioSumClick * 1.5).toInt()
                                                CacasClick += 1
                                            }
                                        },
                                        shape = RoundedCornerShape(5.dp),
                                        border = BorderStroke(2.dp, Color.Black),
                                        colors = ButtonDefaults.buttonColors(containerColor = marron)
                                    ) {
                                        Text("1/s Cost: $PrecioSumClick", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(10.dp))
                        if (mostrarClickUpgrade5) {
                            Spacer(Modifier.height(10.dp))

                            Box {
                                Column {
                                    Text("+1 por click", fontWeight = FontWeight.Bold, color = Color.White)

                                    Button(
                                        onClick = {
                                            if (CacasTotales >= PrecioSumClick) {
                                                CacasTotales -= PrecioSumClick
                                                mostrarClickUpgrade6 = true
                                                PrecioSumClick = (PrecioSumClick * 1.5).toInt()
                                                CacasClick += 1
                                            }
                                        },
                                        shape = RoundedCornerShape(5.dp),
                                        border = BorderStroke(2.dp, Color.Black),
                                        colors = ButtonDefaults.buttonColors(containerColor = marron)
                                    ) {
                                        Text("1/s Cost: $PrecioSumClick", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(10.dp))
                        if (mostrarClickUpgrade6) {
                            Spacer(Modifier.height(10.dp))

                            Box {
                                Column {
                                    Text("+1 por click", fontWeight = FontWeight.Bold, color = Color.White)

                                    Button(
                                        onClick = {
                                            if (CacasTotales >= PrecioSumClick) {
                                                CacasTotales -= PrecioSumClick
                                                mostrarClickUpgrade7 = true
                                                PrecioSumClick = (PrecioSumClick * 1.5).toInt()
                                                CacasClick += 1
                                            }
                                        },
                                        shape = RoundedCornerShape(5.dp),
                                        border = BorderStroke(2.dp, Color.Black),
                                        colors = ButtonDefaults.buttonColors(containerColor = marron)
                                    ) {
                                        Text("1/s Cost: $PrecioSumClick", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(10.dp))
                        if (mostrarClickUpgrade7) {
                            Spacer(Modifier.height(10.dp))

                            Box {
                                Column {
                                    Text("+1 por click", fontWeight = FontWeight.Bold, color = Color.White)

                                    Button(
                                        onClick = {
                                            if (CacasTotales >= PrecioSumClick) {
                                                CacasTotales -= PrecioSumClick
                                                mostrarClickUpgrade8 = true
                                                PrecioSumClick = (PrecioSumClick * 1.5).toInt()
                                                CacasClick += 1
                                            }
                                        },
                                        shape = RoundedCornerShape(5.dp),
                                        border = BorderStroke(2.dp, Color.Black),
                                        colors = ButtonDefaults.buttonColors(containerColor = marron)
                                    ) {
                                        Text("1/s Cost: $PrecioSumClick", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(10.dp))
                        if (mostrarClickUpgrade8) {
                            Spacer(Modifier.height(10.dp))

                            Box {
                                Column {
                                    Text("+1 por click", fontWeight = FontWeight.Bold, color = Color.White)

                                    Button(
                                        onClick = {
                                            if (CacasTotales >= PrecioSumClick) {
                                                CacasTotales -= PrecioSumClick
                                                PrecioSumClick = (PrecioSumClick * 1.5).toInt()
                                                CacasClick += 1
                                            }
                                        },
                                        shape = RoundedCornerShape(5.dp),
                                        border = BorderStroke(2.dp, Color.Black),
                                        colors = ButtonDefaults.buttonColors(containerColor = marron)
                                    ) {
                                        Text("1/s Cost: $PrecioSumClick", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    }
                                }
                            }
                        }
                        Spacer(Modifier.height(10.dp))





                    }

                }



            }
        }
    }
}

