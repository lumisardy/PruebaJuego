package com.example.pruebajuego

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.widget.Space
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
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pruebajuego.ui.theme.NegroTrans
import com.example.pruebajuego.ui.theme.PopsFont
import com.example.pruebajuego.ui.theme.RetroFont
import com.example.pruebajuego.ui.theme.marron
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(){


    val context = LocalContext.current
    val soundManager = remember { SoundManager(context) }

    var CacasTotales by remember { mutableStateOf(0f) }
    var autoSumar by remember { mutableStateOf(false) }
    var CatidadSumar by remember { mutableStateOf(0f) }
    var CantidadTotalCacas by remember { mutableStateOf(0) }





    var PrecioYourBath by remember { mutableStateOf(20) }
    var PrecioSumClick by remember { mutableStateOf(30) }
    var PrecioInodoros by remember { mutableStateOf(1000) }
    var PrecioPopCLiker by remember { mutableStateOf(5000) }
    var PrecioPublicBath by remember { mutableStateOf(20000) }
    var PrecioVertedero by remember { mutableStateOf(100000) }
    var PrecioPopCLiker2 by remember { mutableStateOf(500000) }
    var PrecioAnimals by remember { mutableStateOf(2000000) }
    var PrecioBestPopCLiker by remember { mutableStateOf(5000000) }




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
    var isPressedTop by remember { mutableStateOf(false) }
    var isPressedBottom by remember { mutableStateOf(false) }

    // Animamos el valor de escala
    val scale by animateFloatAsState(
        targetValue = if (isPressedTop) 0.85f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "scaleTopAnim"
    )

    val scale2 by animateFloatAsState(
        targetValue = if (isPressedBottom) 0.85f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "scaleBottomAnim"
    )


    if (CantidadTotalCacas < CacasTotales){

        CantidadTotalCacas = CacasTotales.toInt()

    }


    LaunchedEffect(autoSumar) {
        while (autoSumar) {
            delay(100L)
            CacasTotales += CatidadSumar
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo), // Reemplaza con tu imagen
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop)



        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start)  {
            Box(Modifier.fillMaxWidth().fillMaxHeight(0.1f), contentAlignment = Alignment.BottomCenter){


                Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Start) {

                    Card(Modifier.size(62.dp), colors = CardDefaults.cardColors(containerColor = Color.Transparent)){

                        Spacer(Modifier.height(10.dp))
                        Image(painter = painterResource(id = R.drawable.ajustes),
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.fillMaxSize(),
                                contentDescription = null)

                    }




                    Spacer(Modifier.width(30.dp))

                    Box(Modifier.height(100.dp)){

                        Image(painter = painterResource(id = R.drawable.cartel),
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier.fillMaxWidth(0.8f).fillMaxHeight(),
                            contentDescription = null)


                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            Text("Pops: ${CacasTotales.toInt()}", fontFamily = RetroFont , fontSize = 10.sp, letterSpacing = 3.sp)
                            Text("${(CatidadSumar * 10).toInt()} /s",fontFamily = RetroFont, fontSize = 6.sp)

                        }
                    }





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
                            isPressedTop = true
                            soundManager.playPedoSound()
                            isPressed = true
                            CacasTotales += CacasClick
                            // Volver al tamaño normal después de un pequeño delay
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(100L)
                                isPressedTop = false
                            }
                        }
                )}



                Card(modifier = Modifier.weight(1f).fillMaxHeight(0.7f).background(Color.Transparent), shape = RectangleShape) {


                    Box(modifier = Modifier.fillMaxSize().background(Color.Transparent), contentAlignment = Alignment.BottomCenter) {


                        Image(painter = painterResource(id = R.drawable.fondorama), // Reemplaza con tu imagen
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds)



                        Column(Modifier.fillMaxHeight(0.85f).fillMaxWidth().background(Color.Transparent).verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {

                            val mpCompra: MediaPlayer = MediaPlayer.create(LocalContext.current,R.raw.comprar)

                            Box(Modifier.fillMaxWidth().height(90.dp)){

                                        Image(painter = painterResource(R.drawable.marcoesponja),
                                                contentDescription = null,
                                                modifier = Modifier.fillMaxSize()
                                                    .graphicsLayer(
                                                    scaleX = scale2,
                                                    scaleY = scale2)

                                                 .clickable {
                                                    if (CacasTotales >= PrecioYourBath) {
                                                    CacasTotales -= PrecioYourBath
                                                    autoSumar = true
                                                        isPressedBottom = true
                                                    CatidadSumar += 0.1f
                                                    mostrarClickUpgrade = true
                                                    PrecioYourBath = (PrecioYourBath * 1.15).toInt()
                                                    mpCompra.start()
                                                        CoroutineScope(Dispatchers.Main).launch {
                                                            delay(100L)
                                                            isPressedBottom = false
                                                        }
                                                } },
                                                contentScale = ContentScale.FillBounds)

                                        Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 50.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                        {

                                            Text("Your bath", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 15.sp)


                                            Text("1/s  $PrecioYourBath $", fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 18.sp)


                                        }










                            }

                            Spacer(Modifier.height(10.dp))

                            if (mostrarClickUpgrade) {
                                Spacer(Modifier.height(10.dp))

                                Box() {
                                    Column {
                                        Text("Better clicks", fontWeight = FontWeight.Bold,fontFamily = RetroFont, color = Color.White)

                                        Button(
                                            onClick = {
                                                if (CacasTotales >= PrecioSumClick) {
                                                    CacasTotales -= PrecioSumClick
                                                    mostrarClickUpgrade2 = true
                                                    PrecioSumClick = (PrecioSumClick * 1.15).toInt()
                                                    CacasClick += 1
                                                    mpCompra.start()
                                                }
                                            },
                                            shape = RoundedCornerShape(5.dp),
                                            border = BorderStroke(2.dp, Color.Black),
                                            colors = ButtonDefaults.buttonColors(containerColor = marron)
                                        ) {
                                            Text("+1 click: $PrecioSumClick $", fontWeight = FontWeight.Bold,fontFamily = RetroFont, fontSize = 18.sp)
                                        }
                                    }
                                }
                            }

                            Spacer(Modifier.height(10.dp))

                            if (mostrarClickUpgrade2) {
                                Spacer(Modifier.height(10.dp))

                                Box {
                                    Column {
                                        Text("Inodoros", fontWeight = FontWeight.Bold,fontFamily = RetroFont, color = Color.White)

                                        Button(
                                            onClick = {
                                                if (CacasTotales >= PrecioInodoros) {
                                                    CacasTotales -= PrecioInodoros
                                                    mostrarClickUpgrade3 = true
                                                    PrecioInodoros = (PrecioInodoros * 1.15).toInt()
                                                    CatidadSumar += 1
                                                    mpCompra.start()
                                                }
                                            },
                                            shape = RoundedCornerShape(5.dp),
                                            border = BorderStroke(2.dp, Color.Black),
                                            colors = ButtonDefaults.buttonColors(containerColor = marron)
                                        ) {
                                            Text("10/s $PrecioInodoros $", fontWeight = FontWeight.Bold,fontFamily = RetroFont, fontSize = 18.sp)
                                        }
                                    }
                                }
                            }

                            Spacer(Modifier.height(10.dp))

                            if (mostrarClickUpgrade3) {
                                Spacer(Modifier.height(10.dp))

                                Box {
                                    Column {
                                        Text("The Pop click", fontWeight = FontWeight.Bold,fontFamily = RetroFont, color = Color.White)

                                        Button(
                                            onClick = {
                                                if (CacasTotales >= PrecioPopCLiker) {
                                                    CacasTotales -= PrecioPopCLiker
                                                    mostrarClickUpgrade4 = true
                                                    PrecioPopCLiker = (PrecioPopCLiker * 1.2).toInt()
                                                    CacasClick += 5
                                                    mpCompra.start()
                                                }
                                            },
                                            shape = RoundedCornerShape(5.dp),
                                            border = BorderStroke(2.dp, Color.Black),
                                            colors = ButtonDefaults.buttonColors(containerColor = marron)
                                        ) {
                                            Text("+5 click  $PrecioPopCLiker $", fontWeight = FontWeight.Bold,fontFamily = RetroFont, fontSize = 18.sp)
                                        }
                                    }
                                }
                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade4) {
                                Spacer(Modifier.height(10.dp))

                                Box {
                                    Column {
                                        Text("Public Bathrooms", fontWeight = FontWeight.Bold,fontFamily = RetroFont, color = Color.White)

                                        Button(
                                            onClick = {
                                                if (CacasTotales >= PrecioPublicBath) {
                                                    CacasTotales -= PrecioPublicBath
                                                    mostrarClickUpgrade5 = true
                                                    PrecioPublicBath = (PrecioPublicBath * 1.2).toInt()
                                                    CatidadSumar += 5
                                                    mpCompra.start()
                                                }
                                            },
                                            shape = RoundedCornerShape(5.dp),
                                            border = BorderStroke(2.dp, Color.Black),
                                            colors = ButtonDefaults.buttonColors(containerColor = marron)
                                        ) {
                                            Text("50/s  $PrecioPublicBath $", fontWeight = FontWeight.Bold,fontFamily = RetroFont, fontSize = 18.sp)
                                        }
                                    }
                                }
                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade5) {
                                Spacer(Modifier.height(10.dp))

                                Box {
                                    Column {
                                        Text("Vertedero", fontWeight = FontWeight.Bold,fontFamily = RetroFont, color = Color.White)

                                        Button(
                                            onClick = {
                                                if (CacasTotales >= PrecioVertedero) {
                                                    CacasTotales -= PrecioVertedero
                                                    mostrarClickUpgrade6 = true
                                                    PrecioVertedero = (PrecioVertedero * 1.15).toInt()
                                                    CatidadSumar += 50
                                                    mpCompra.start()
                                                }
                                            },
                                            shape = RoundedCornerShape(5.dp),
                                            border = BorderStroke(2.dp, Color.Black),
                                            colors = ButtonDefaults.buttonColors(containerColor = marron)
                                        ) {
                                            Text("500/s $PrecioVertedero $", fontWeight = FontWeight.Bold,fontFamily = RetroFont, fontSize = 18.sp)
                                        }
                                    }
                                }
                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade6) {
                                Spacer(Modifier.height(10.dp))

                                Box {
                                    Column {
                                        Text("THE BEST TAP POP", fontWeight = FontWeight.Bold,fontFamily = RetroFont, color = Color.White)

                                        Button(
                                            onClick = {
                                                if (CacasTotales >= PrecioPopCLiker2) {
                                                    CacasTotales -= PrecioPopCLiker2
                                                    mostrarClickUpgrade7 = true
                                                    PrecioPopCLiker2 = (PrecioPopCLiker2 * 1.15).toInt()
                                                    CacasClick += 50
                                                    mpCompra.start()
                                                }
                                            },
                                            shape = RoundedCornerShape(5.dp),
                                            border = BorderStroke(2.dp, Color.Black),
                                            colors = ButtonDefaults.buttonColors(containerColor = marron)
                                        ) {
                                            Text("+50 click $PrecioPopCLiker2 $", fontWeight = FontWeight.Bold,fontFamily = RetroFont, fontSize = 18.sp)
                                        }
                                    }
                                }
                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade7) {
                                Spacer(Modifier.height(10.dp))

                                Box {
                                    Column {
                                        Text("Animals pop? yea", fontWeight = FontWeight.Bold,fontFamily = RetroFont, color = Color.White)

                                        Button(
                                            onClick = {
                                                if (CacasTotales >= PrecioAnimals) {
                                                    CacasTotales -= PrecioAnimals
                                                    mostrarClickUpgrade8 = true
                                                    PrecioAnimals = (PrecioAnimals * 1.15).toInt()
                                                    CatidadSumar += 250
                                                    mpCompra.start()
                                                }
                                            },
                                            shape = RoundedCornerShape(5.dp),
                                            border = BorderStroke(2.dp, Color.Black),
                                            colors = ButtonDefaults.buttonColors(containerColor = marron)
                                        ) {
                                            Text("2500/s $PrecioAnimals $", fontWeight = FontWeight.Bold,fontFamily = RetroFont, fontSize = 18.sp)
                                        }
                                    }
                                }
                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade8) {
                                Spacer(Modifier.height(10.dp))

                                Box {
                                    Column {
                                        Text("OMG THE POP FINGER", fontWeight = FontWeight.Bold,fontFamily = RetroFont, color = Color.White)

                                        Button(
                                            onClick = {
                                                if (CacasTotales >= PrecioBestPopCLiker) {
                                                    CacasTotales -= PrecioBestPopCLiker
                                                    PrecioBestPopCLiker = (PrecioBestPopCLiker * 1.15).toInt()
                                                    CacasClick += 500
                                                    mpCompra.start()
                                                }
                                            },
                                            shape = RoundedCornerShape(5.dp),
                                            border = BorderStroke(2.dp, Color.Black),
                                            colors = ButtonDefaults.buttonColors(containerColor = marron)
                                        ) {
                                            Text("+500 click $PrecioBestPopCLiker $", fontWeight = FontWeight.Bold,fontFamily = RetroFont, fontSize = 18.sp)
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
}

