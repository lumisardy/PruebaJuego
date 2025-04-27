package com.example.pruebajuego

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.widget.Space
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pruebajuego.OptimizacionesComposables.CacaImage
import com.example.pruebajuego.OptimizacionesComposables.PoopViewModel
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
    val poopViewModel: PoopViewModel = remember { PoopViewModel(context) }
    val soundManager = remember { SoundManager(context) }

    // Observamos los valores de StateFlow usando collectAsState()
    val poopData = poopViewModel.poopData.collectAsState()

    val CacasTotales = poopData.value.cacasTotales
    val CatidadSumar = poopData.value.cantidadSumar
    val CantidadTotalCacas = poopData.value.cantidadCacaTotales

    val PrecioYourBath = poopData.value.precioYourBath
    val PrecioSumClick = poopData.value.precioSumClick
    val PrecioInodoros = poopData.value.precioInodoros
    val PrecioPopCLiker = poopData.value.precioPopClicker
    val PrecioPublicBath = poopData.value.precioPublicBath
    val PrecioVertedero = poopData.value.precioVertedero
    val PrecioAnimals = poopData.value.precioAnimals
    val PrecioPopCLiker2 = poopData.value.precioPopClicker2
    val PrecioBestPopCLiker = poopData.value.precioBestPopClicker

    val mostrarClickUpgrade = poopData.value.mostrarClickUpgrade
    val mostrarClickUpgrade2 = poopData.value.mostrarClickUpgrade2
    val mostrarClickUpgrade3 = poopData.value.mostrarClickUpgrade3
    val mostrarClickUpgrade4 = poopData.value.mostrarClickUpgrade4
    val mostrarClickUpgrade5 = poopData.value.mostrarClickUpgrade5
    val mostrarClickUpgrade6 = poopData.value.mostrarClickUpgrade6
    val mostrarClickUpgrade7 = poopData.value.mostrarClickUpgrade7
    val mostrarClickUpgrade8 = poopData.value.mostrarClickUpgrade8








    var isPressedTop by remember { mutableStateOf(false) }
    var isPressedBottom by remember { mutableStateOf(false) }

    // Animamos el valor de escala
    val scaleAnim = remember { Animatable(1f) }
    val coroutineScope = rememberCoroutineScope()

    val scale2 by animateFloatAsState(
        targetValue = if (isPressedBottom) 0.85f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "scaleBottomAnim"
    )


    poopViewModel.actualizarCantidadTotalCacas()




    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo), // Reemplaza con tu imagen
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop)



        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start)  {
            Box(Modifier.fillMaxWidth().fillMaxHeight(0.15f), contentAlignment = Alignment.TopCenter){


                Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Start) {

                    Card(Modifier.height(90.dp).width(50.dp).padding(5.dp), colors = CardDefaults.cardColors(containerColor = Color.Transparent)){

                        Spacer(Modifier.height(30.dp))
                        Image(painter = painterResource(id = R.drawable.ajustes),
                                contentScale = ContentScale.FillHeight,
                                modifier = Modifier.fillMaxSize(),
                                contentDescription = null)
                    }




                    Spacer(Modifier.width(30.dp))

                    Box(Modifier.height(140.dp), contentAlignment = Alignment.Center){

                        Image(painter = painterResource(id = R.drawable.cartel),
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.fillMaxWidth(0.8f).fillMaxHeight(),
                            contentDescription = null)


                        Column(modifier = Modifier.padding(top = 20.dp),         horizontalAlignment = Alignment.CenterHorizontally) {

                            Text("Poops: ${formatPoops(CacasTotales.toInt())}", fontFamily = RetroFont , fontSize = 16.sp, color = Color.White, letterSpacing = 3.sp)
                            Spacer(Modifier.height(20.dp))
                            Text("${formatPoops((CatidadSumar * 10).toInt())}/s",fontFamily = RetroFont, fontSize = 16.sp,color = Color.White)

                        }
                    }





                }




            }


            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {


                Box(modifier = Modifier.weight(1f).padding(20.dp), contentAlignment = Alignment.Center){



                    CacaImage(
                        cantidadTotalCacas = CantidadTotalCacas,
                        scale = scaleAnim.value,
                        soundManager = soundManager,
                        onClickAction = {
                            soundManager.playPedoSound()
                            poopViewModel.clickCaca()

                            coroutineScope.launch {
                                scaleAnim.animateTo(
                                    targetValue = 0.85f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessMedium
                                    )
                                )
                                scaleAnim.animateTo(
                                    targetValue = 1f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioLowBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            }
                        }
                    )
                }



                Card(modifier = Modifier.weight(1f).fillMaxHeight(0.7f), shape = RectangleShape, colors = CardDefaults.cardColors(
                    Color.Transparent)) {


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
                                                     mpCompra.start()
                                                     poopViewModel.comprarMejoraYourBath()
                                                },
                                                contentScale = ContentScale.FillBounds)

                                        Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 50.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                        {

                                            Text("Your bath", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 15.sp)


                                            Text("1/s  ${formatPoops(PrecioYourBath)}$", fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 18.sp)


                                        }










                            }

                            Spacer(Modifier.height(10.dp))

                            if (mostrarClickUpgrade) {
                                Spacer(Modifier.height(10.dp))

                                Box(Modifier.fillMaxWidth().height(90.dp)) {



                                    Image(painter = painterResource(R.drawable.marcoclick),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {
                                                poopViewModel.comprarMejorClick()
                                                mpCompra.start()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }
                                                       },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 50.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Better clicks", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 15.sp)


                                        Text("+1 click: ${formatPoops(PrecioSumClick)}$",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 14.sp)


                                    }

                                }
                            }

                            Spacer(Modifier.height(10.dp))

                            if (mostrarClickUpgrade2) {
                                Spacer(Modifier.height(10.dp))




                                Box(Modifier.fillMaxWidth().height(90.dp)) {



                                    Image(painter = painterResource(R.drawable.marcobanos),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()

                                                poopViewModel.comprarMejoraInodoros()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                                },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 50.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Inodoros", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 15.sp)


                                        Text("10/s ${formatPoops(PrecioInodoros)} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 14.sp)


                                    }

                                }

                            }

                            Spacer(Modifier.height(10.dp))

                            if (mostrarClickUpgrade3) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth().height(90.dp)) {



                                    Image(painter = painterResource(R.drawable.marcoclick2),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.mejoraPoopClick()

                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 50.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("The Pop click", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 15.sp)


                                        Text("+5 click  ${formatPoops(PrecioPopCLiker)}$",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 14.sp)


                                    }

                                }

                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade4) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth().height(90.dp)) {



                                    Image(painter = painterResource(R.drawable.marcoclick2),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.publicBathrooms()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 50.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Public Bathrooms", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 15.sp)


                                        Text("50/s  ${formatPoops(PrecioPublicBath)} \$",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 14.sp)


                                    }

                                }


                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade5) {
                                Spacer(Modifier.height(10.dp))



                                Box(Modifier.fillMaxWidth().height(90.dp)) {



                                    Image(painter = painterResource(R.drawable.marcoclick2),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.mejoraVertedero()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 50.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Vertedero", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 15.sp)


                                        Text("500/s ${formatPoops(PrecioVertedero)} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 14.sp)


                                    }

                                }


                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade6) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth().height(90.dp)) {



                                    Image(painter = painterResource(R.drawable.marcoclick2),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.mejoratheBestTapPoop()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 50.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Poop Cleaner", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 15.sp)


                                        Text("+50 click ${formatPoops(PrecioPopCLiker2)} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 14.sp)


                                    }

                                }

                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade7) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth().height(90.dp)) {



                                    Image(painter = painterResource(R.drawable.marcoclick2),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.mejoraAnimalPoop()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 50.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Animals poops", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 15.sp)


                                        Text("2500/s ${formatPoops(PrecioAnimals)} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 14.sp)


                                    }

                                }

                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade8) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth().height(90.dp)) {



                                    Image(painter = painterResource(R.drawable.marcoclick2),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.mejoraPoopFinger()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 50.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("POP FINGER", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 15.sp)


                                        Text("+500 click ${formatPoops(PrecioBestPopCLiker)} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 14.sp)


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


fun formatPoops(cacasTotales: Int): String {
    return when {
        cacasTotales >= 1_000_000_000 -> "${cacasTotales / 1_000_000_000}b"
        cacasTotales >= 1_000_000 -> "${cacasTotales / 1_000_000}m"
        cacasTotales >= 10_000 -> "${cacasTotales / 1_000}k"
        else -> "$cacasTotales"
    }
}

