package com.example.pruebajuego

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
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
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.window.Dialog
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

    val audioViewModel: AudioViewModel = viewModel()

    // *** Asegúrate de liberar los recursos del SoundManager al salir del Composable ***
    DisposableEffect(Unit) { // Importa DisposableEffect
        onDispose {
            soundManager.release()
        }
    }

    val musicVolume by audioViewModel.musicVolume.collectAsState()

    // Observamos los valores de StateFlow usando collectAsState()
    val poopData = poopViewModel.poopData.collectAsState()

    val CacasTotales = poopData.value.cacasTotales
    val CatidadSumar = poopData.value.cantidadSumar
    val CantidadTotalCacas = poopData.value.cantidadCacaTotales
    LaunchedEffect(Unit) { // Unit significa que solo se ejecuta la primera vez
        audioViewModel.startBackgroundMusic(context)
    }

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
    val mostrarClickUpgrade9 = poopData.value.mostrarClickUpgrade9
    val mostrarClickUpgrade10 = poopData.value.mostrarClickUpgrade10
    val mostrarClickUpgrade11 = poopData.value.mostrarClickUpgrade11
    val mostrarClickUpgrade12 = poopData.value.mostrarClickUpgrade12
    val mostrarClickUpgrade13 = poopData.value.mostrarClickUpgrade13
    val mostrarClickUpgrade14 = poopData.value.mostrarClickUpgrade14
    val mostrarClickUpgrade15 = poopData.value.mostrarClickUpgrade15
    val mostrarClickUpgrade16 = poopData.value.mostrarClickUpgrade16
    val mostrarClickUpgrade17 = poopData.value.mostrarClickUpgrade17
    val mostrarClickUpgrade18 = poopData.value.mostrarClickUpgrade18
    val mostrarClickUpgrade19 = poopData.value.mostrarClickUpgrade19
    val mostrarClickUpgrade20 = poopData.value.mostrarClickUpgrade20
    val mostrarClickUpgrade21 = poopData.value.mostrarClickUpgrade21
    val mostrarClickUpgrade22 = poopData.value.mostrarClickUpgrade22





    var showSettingsDialog by remember { mutableStateOf(false) }

    // Estados para los valores de los sliders (ejemplo)

    var effectsVolume by remember { mutableStateOf(0.7f) }



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

                    Card(Modifier.height(95.dp).width(70.dp).padding(5.dp), colors = CardDefaults.cardColors(containerColor = Color.Transparent)){

                        Spacer(Modifier.height(30.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ajustes), // Reemplaza R.drawable.cartel con tu recurso de imagen
                            contentScale = ContentScale.FillBounds,
                            contentDescription = "Icono de Ajustes", // Agrega una descripción accesible
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()

                                .clickable {
                                    showSettingsDialog = true // Abre el diálogo al hacer click
                                }
                        )

                        // El Diálogo de Ajustes
                        if (showSettingsDialog) {
                            Dialog(onDismissRequest = { showSettingsDialog = false }) {
                                Card(colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
                                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                        Image(

                                            painter = painterResource(id = R.drawable.fondoajustes), // Reemplaza R.drawable.cartel con tu recurso de imagen

                                            contentScale = ContentScale.FillBounds,

                                            contentDescription = "Ajustes", // Agrega una descripción accesible

                                            modifier = Modifier

                                                .fillMaxHeight(0.7f)

                                                .fillMaxWidth()



                                        )

                                        Column(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .fillMaxWidth(0.65f)
                                                .fillMaxHeight(0.6f),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(10.dp)
                                        ) {
                                            Text("Ajustes", fontFamily = RetroFont, color = Color.White)
                                            Spacer(Modifier.height(15.dp))

                                            // Slider para la Música
                                            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.weight(1f)) {
                                                Text("Música:",fontFamily = RetroFont, color = Color.White)
                                                Slider(
                                                    // *** Usar el valor del ViewModel ***
                                                    value = musicVolume,
                                                    onValueChange = {
                                                        // *** Llamar a la función del ViewModel para cambiar el volumen ***
                                                        audioViewModel.setMusicVolume(it)
                                                    },
                                                    valueRange = 0f..1f,
                                                    modifier = Modifier.weight(1f)
                                                )
                                            }

                                            // Slider para los Efectos
                                            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.weight(1f)) {
                                                Text("Efectos:",fontFamily = RetroFont, color = Color.White)
                                                Slider(
                                                    value = effectsVolume, // Usa el estado local para el slider
                                                    onValueChange = {
                                                        effectsVolume = it // 1. Actualiza el estado local
                                                        // *** ¡DESCOMENTA Y ACTIVA ESTA LLAMADA! ***
                                                        soundManager.setEffectsVolume(it) // <-- Llama a la función del SoundManager
                                                    },
                                                    valueRange = 0f..1f,
                                                    modifier = Modifier.weight(1f)
                                                )
                                            }
                                            
                                            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center){

                                                Image( painter = painterResource(id = R.drawable.marcovacio), // Reemplaza R.drawable.cartel con tu recurso de imagen
                                                    contentScale = ContentScale.FillBounds,
                                                    contentDescription = "Cerrar Ajustes",
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .fillMaxHeight(0.8f)
                                                        .padding(6.dp)
                                                        .clickable {
                                                            showSettingsDialog = false
                                                        })

                                                Text("Cerrar", fontFamily = RetroFont, color = Color.White)

                                            }



                                        }

                                    }




                                }
                            }
                        }
                    }




                    Spacer(Modifier.width(30.dp))

                    Box(Modifier.height(140.dp), contentAlignment = Alignment.Center){

                        Image(painter = painterResource(id = R.drawable.cartel),
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.fillMaxWidth(0.8f).fillMaxHeight(),
                            contentDescription = null,)


                        Column(modifier = Modifier.padding(top = 20.dp),         horizontalAlignment = Alignment.CenterHorizontally) {

                            Text("Poops:${formatPoops(CacasTotales.toInt())}", fontFamily = RetroFont , fontSize = 14.sp, color = Color.White, letterSpacing = 3.sp)
                            Spacer(Modifier.height(20.dp))
                            Text("${formatPoops((CatidadSumar * 10).toInt())}/s",fontFamily = RetroFont, fontSize = 16.sp,color = Color.White)

                        }
                    }





                }




            }


            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {


                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center){



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



                Card(modifier = Modifier.weight(1.7f).fillMaxHeight(0.8f), shape = RectangleShape, colors = CardDefaults.cardColors(
                    Color.Transparent)) {


                    Box(modifier = Modifier.fillMaxSize().background(Color.Transparent), contentAlignment = Alignment.Center) {


                        Image(painter = painterResource(id = R.drawable.tronquitofondo), // Reemplaza con tu imagen
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds)



                        Column(Modifier.fillMaxHeight(0.7f).fillMaxWidth().padding(bottom = 30.dp).background(Color.Transparent).verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {

                            val mpCompra: MediaPlayer = MediaPlayer.create(LocalContext.current,R.raw.comprar)

                            Box(Modifier.fillMaxWidth(0.9f).height(80.dp)){

                                        Image(painter = painterResource(R.drawable.marcoesponja),
                                                contentDescription = null,
                                                modifier = Modifier.fillMaxSize()
                                                    .graphicsLayer(
                                                    scaleX = scale2,
                                                    scaleY = scale2)


                                                 .clickable {
                                                     soundManager.playBuySound()
                                                     poopViewModel.comprarMejoraYourBath()
                                                },
                                                contentScale = ContentScale.FillBounds)

                                        Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                        {

                                            Text("Your bath", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 10.sp)


                                            Text("1/s  ${formatPoops(PrecioYourBath)}$", fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 12.sp)


                                        }










                            }

                            Spacer(Modifier.height(10.dp))

                            if (mostrarClickUpgrade) {
                                Spacer(Modifier.height(10.dp))

                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.marcoclick),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {
                                                poopViewModel.comprarMejorClick()
                                                soundManager.playBuySound()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }
                                                       },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Better clicks", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 10.sp)


                                        Text("+1 click: ${formatPoops(PrecioSumClick)}$",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 10.sp)


                                    }

                                }
                            }

                            Spacer(Modifier.height(10.dp))

                            if (mostrarClickUpgrade2) {
                                Spacer(Modifier.height(10.dp))




                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.marcowc),
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

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Inodoros", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 10.sp)


                                        Text("10/s ${formatPoops(PrecioInodoros)} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 12.sp)


                                    }

                                }

                            }

                            Spacer(Modifier.height(10.dp))

                            if (mostrarClickUpgrade3) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



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

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("The Pop click", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 10.sp)


                                        Text("+5 click  ${formatPoops(PrecioPopCLiker.toInt())}$",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 10.sp)


                                    }

                                }

                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade4) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.marcobanos),
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

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Public Bathrooms", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 10.sp)


                                        Text("50/s  ${formatPoops(PrecioPublicBath.toInt())} \$",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 12.sp)


                                    }

                                }


                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade5) {
                                Spacer(Modifier.height(10.dp))



                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.marcobolsabasura),
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

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Vertedero", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 10.sp)


                                        Text("250/s ${formatPoops(PrecioVertedero.toInt())} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 12.sp)


                                    }

                                }


                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade6) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.escoba),
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

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Poop Cleaner", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 10.sp)


                                        Text("+50 click ${formatPoops(PrecioPopCLiker2.toInt())} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 10.sp)


                                    }

                                }

                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade7) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.pajaro),
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

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Animals poops", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 10.sp)


                                        Text("2500/s ${formatPoops(PrecioAnimals.toInt())} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 12.sp)


                                    }

                                }

                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade8) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.marcomanomoneda),
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

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("POP FINGER", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 12.sp)


                                        Text("+500 click ${formatPoops(PrecioBestPopCLiker.toInt())} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 10.sp)


                                    }

                                }

                            }
                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade9) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.marcomanomoneda),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.mejoraBestPooper()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("BestPooper", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 12.sp)


                                        Text("+5k/s ${formatPoops(PrecioBestPopCLiker.toInt())} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 10.sp)


                                    }

                                }

                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade10) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.marcomanomoneda),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.mejoraPoopCities()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Poop Cities", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 12.sp)


                                        Text("+10k/s ${formatPoops(PrecioBestPopCLiker.toInt())} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 10.sp)


                                    }

                                }

                            }


                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade11) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.marcomanomoneda),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.mejoraMultiPoops()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Multi Poops", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 12.sp)


                                        Text("+50k/s ${formatPoops(PrecioBestPopCLiker.toInt())} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 10.sp)


                                    }

                                }

                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade12) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.marcomanomoneda),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.mejoraSmartPoop()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Smart Poop", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 12.sp)


                                        Text("+200k/s ${formatPoops(PrecioBestPopCLiker.toInt())} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 10.sp)


                                    }

                                }

                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade13) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.marcomanomoneda),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.mejoraPoopEconomy()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Poop Economy", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 12.sp)


                                        Text("+1m/s ${formatPoops(PrecioBestPopCLiker.toInt())} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 10.sp)


                                    }

                                }

                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade14) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.marcomanomoneda),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.mejoraFireFinguer()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Fire Finguer", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 12.sp)


                                        Text("+10k click ${formatPoops(PrecioBestPopCLiker.toInt())} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 10.sp)


                                    }

                                }

                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade15) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.marcomanomoneda),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.mejoraAncientPoops()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Ancient Poops", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 12.sp)


                                        Text("+50m/s ${formatPoops(PrecioBestPopCLiker.toInt())} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 10.sp)


                                    }

                                }

                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade16) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.marcomanomoneda),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.mejoraPoopEarht()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Poop Earht", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 12.sp)


                                        Text("+200m/s ${formatPoops(PrecioBestPopCLiker.toInt())} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 10.sp)


                                    }

                                }

                            }


                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade17) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.marcomanomoneda),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.mejoraPoopStars()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Poop Stars?", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 12.sp)


                                        Text("+1b/s ${formatPoops(PrecioBestPopCLiker.toInt())} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 10.sp)


                                    }

                                }

                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade18) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.marcomanomoneda),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.mejoraToolsClick()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Tools Click", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 12.sp)


                                        Text("+100k/s ${formatPoops(PrecioBestPopCLiker.toInt())} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 10.sp)


                                    }

                                }

                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade19) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.marcomanomoneda),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.mejoraCleanPoops()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Clean Poops?", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 12.sp)


                                        Text("+50b/s ${formatPoops(PrecioBestPopCLiker.toInt())} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 10.sp)


                                    }

                                }

                            }


                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade20) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.marcomanomoneda),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.mejoraAscendPoops()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Ascend Poops", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 12.sp)


                                        Text("+1t/s ${formatPoops(PrecioBestPopCLiker.toInt())} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 10.sp)


                                    }

                                }

                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade21) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.marcomanomoneda),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.mejoraPoopAge()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Poop Age", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 12.sp)


                                        Text("+200t/s ${formatPoops(PrecioBestPopCLiker.toInt())} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 10.sp)


                                    }

                                }

                            }

                            Spacer(Modifier.height(10.dp))
                            if (mostrarClickUpgrade22) {
                                Spacer(Modifier.height(10.dp))


                                Box(Modifier.fillMaxWidth(0.9f).height(80.dp)) {



                                    Image(painter = painterResource(R.drawable.marcomanomoneda),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                            .graphicsLayer(
                                                scaleX = scale2,
                                                scaleY = scale2)

                                            .clickable {

                                                mpCompra.start()
                                                poopViewModel.mejoraCliker()
                                                isPressedBottom = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    delay(100L)
                                                    isPressedBottom = false
                                                }

                                            },
                                        contentScale = ContentScale.FillBounds)

                                    Column(Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center)
                                    {

                                        Text("Cliker?", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = RetroFont, fontSize = 12.sp)


                                        Text("+10b click ${formatPoops(PrecioBestPopCLiker.toInt())} $",  fontWeight = FontWeight.Bold,fontFamily = RetroFont,color = Color.White, fontSize = 10.sp)


                                    }

                                }

                            }





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
        cacasTotales >= 10_000_000 -> "${cacasTotales / 1_000_000}m"
        cacasTotales >= 10_000 -> "${cacasTotales / 1_000}k"
        else -> "$cacasTotales"
    }
}

