package com.example.pruebajuego.core.navigation

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pruebajuego.AudioViewModel

import com.example.pruebajuego.EntryScreen
import com.example.pruebajuego.MainScreen
import com.example.pruebajuego.R
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationWrapper(){
    val navController = rememberNavController()
    var showBottomBar  by remember { mutableStateOf(false) }

    Scaffold(bottomBar =  { if (showBottomBar) {
        BottomBarNav(navController)  // La BottomBar solo se muestra cuando showBottomBar es true
    } }){  innerPadding ->


        NavHost(navController = navController, startDestination = Entry){
            composable<Main>{
                showBottomBar  = true
                MainScreen()
            }

            composable<Entry>{
                EntryScreen({navController.navigate(Main)})
            }




        }



    }



}


fun Any.toRoute(): String = this::class.qualifiedName ?: error("Unnamed route")

@Composable
fun BottomBarNav(navController: NavHostController) {

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route



    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets,
        modifier = Modifier
            .height(120.dp)
            .drawBehind {
                // Dibujamos el fondo con un gradiente
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = -30f,
                        endY = size.height
                    ),
                    topLeft = Offset(0f, -30f)
                )
            },
        containerColor = Color.White
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.partedeabajo3),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    ,  // Ajusta la altura de la imagen para que no ocupe toda la altura
                contentDescription = null
            )

            // Los ítems de navegación ahora están encima de la imagen
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.Center)  // Alineamos los íconos en el centro vertical
            ) {
                NavigationBarItem(
                    selected = false,
                    enabled = false,
                    onClick = { navController.navigate(Main) },
                    icon = {
                        Box(
                            modifier = Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 20.dp, bottom = 5.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.iconcaca),
                                    contentScale = ContentScale.FillHeight,
                                    modifier = Modifier.fillMaxSize(),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = {
                        Box(
                            modifier = Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 25.dp, bottom = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.iconlevelup),
                                    contentScale = ContentScale.FillHeight,
                                    modifier = Modifier.fillMaxSize(),
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    label = {

                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = {
                        Box(
                            modifier = Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 22.dp, bottom = 5.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.iconcofre),
                                    contentScale = ContentScale.FillHeight,
                                    modifier = Modifier.fillMaxSize(),
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    label = {

                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(Icons.Filled.Favorite, contentDescription = null, tint = Color.Black)
                    },
                    label = {

                    }
                )
            }
        }
    }

}

