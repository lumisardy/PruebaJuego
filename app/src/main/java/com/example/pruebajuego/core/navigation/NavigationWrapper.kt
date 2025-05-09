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
import androidx.compose.runtime.DisposableEffect
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
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
import com.example.pruebajuego.Screens.UpgradesScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationWrapper(){
    val navController = rememberNavController()
    var showBottomBar  by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Obtiene el ViewModelStoreOwner del propietario del ciclo de vida local (generalmente la Activity)
    val viewModelStoreOwner = LocalLifecycleOwner.current as ViewModelStoreOwner
    // Obtiene el LifecycleOwner para observar los eventos del ciclo de vida
    val lifecycleOwner = LocalLifecycleOwner.current


    // *** Obtén el AudioViewModel usando el owner de la Activity y la Factory ***
    // Esto asegura que el ViewModel vive tanto como la Activity que contiene este Composable
    val audioViewModel: AudioViewModel = viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        factory = AudioViewModel.Factory(context) // Usa la Factory que definimos en AudioViewModel
    )

    // *** Gestiona la reproducción de audio observando el ciclo de vida ***
    DisposableEffect(lifecycleOwner) { // La key es lifecycleOwner para que se re-ejecute si cambia (raro, pero buena práctica)
        Log.d("NavigationWrapper", "DisposableEffect: Inicializando observador de ciclo de vida para audio.")

        // Crea un observador de eventos del ciclo de vida
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    // Cuando la Activity vuelve al primer plano
                    Log.d("NavigationWrapper", "Lifecycle.Event.ON_START: Iniciando/Reanudando audio.")
                    audioViewModel.startBackgroundMusic() // startBackgroundMusic maneja iniciar o reanudar
                }
                Lifecycle.Event.ON_STOP -> {
                    // Cuando la Activity va a segundo plano
                    Log.d("NavigationWrapper", "Lifecycle.Event.ON_STOP: Pausando audio.")
                    audioViewModel.pauseBackgroundMusic() // Pausa la música
                }
                // Puedes añadir otros eventos si necesitas (ej: ON_PAUSE, ON_RESUME, ON_CREATE, ON_DESTROY)
                else -> {
                    // Log.d("NavigationWrapper", "Lifecycle Event: $event") // Opcional: loguear otros eventos
                }
            }
        }

        // Añade el observador al ciclo de vida del propietario
        lifecycleOwner.lifecycle.addObserver(observer)

        // El bloque onDispose se ejecuta cuando el Composable sale de composición
        onDispose {
            Log.d("NavigationWrapper", "DisposableEffect: Limpiando observador y deteniendo audio.")
            // Remueve el observador para evitar fugas de memoria
            lifecycleOwner.lifecycle.removeObserver(observer)
            // Detiene y libera completamente el reproductor cuando NavigationWrapper es destruido
            audioViewModel.stopBackgroundMusic() // stopBackgroundMusic ahora solo detiene, onCleared libera
        }
    }


    Scaffold(bottomBar =  {
        // Controla si la BottomBar se muestra o no
        if (showBottomBar) {
            BottomBarNav(navController)
        }
    }){  innerPadding -> // innerPadding no se usa en este Scaffold básico, pero se mantiene por si acaso

        NavHost(navController = navController, startDestination = Entry){ // Asegúrate de que Entry es la ruta correcta de inicio
            composable<Main>{ // Usa rutas seguras si las tienes definidas
                showBottomBar  = true // Muestra la BottomBar en la pantalla Main
                MainScreen(audioViewModel = audioViewModel) // Tu Composable de la pantalla principal
            }

            composable<Entry>{ // Usa rutas seguras si las tienes definidas
                showBottomBar = false // Oculta la BottomBar en la pantalla de entrada
                EntryScreen({navController.navigate(Main)}) // Tu Composable de la pantalla de entrada
            }

            composable<Upgrades>{ // Usa rutas seguras si las tienes definidas
                showBottomBar = true // Muestra la BottomBar en la pantalla de mejoras
                UpgradesScreen() // Tu Composable de la pantalla de mejoras
            }

            // Añade composables para otras pantallas aquí
            // composable<OtraPantalla> { ... }
        }
    }
}

// Función de extensión para obtener la ruta segura (si usas sealed classes/objects para rutas)
// Si usas rutas string simples ("main", "entry"), puedes eliminar esta función.
// fun Any.toRoute(): String = this::class.qualifiedName ?: error("Unnamed route")


@Composable
fun BottomBarNav(navController: NavHostController) {

    // Obtener la ruta actual para resaltar el ítem seleccionado (opcional)
    // val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets,
        modifier = Modifier
            .height(120.dp) // Ajusta la altura según necesites
            .drawBehind {
                // Dibujamos el fondo con un gradiente
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = -30f, // Ajusta startY y endY para controlar el gradiente
                        endY = size.height
                    ),
                    topLeft = Offset(0f, -30f)
                )
            },
        containerColor = Color.White // Color del contenedor (puede ser transparente si la imagen cubre todo)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Imagen de fondo de la BottomBar
            Image(
                painter = painterResource(id = R.drawable.partedeabajo3), // Asegúrate de que el recurso existe
                contentScale = ContentScale.FillBounds, // O FillWidth/FillHeight según cómo quieras que se ajuste
                modifier = Modifier.fillMaxSize(),
                contentDescription = null // Decorativa
            )

            // Los ítems de navegación (Row con NavigationBarItems)
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.Center) // Centra la fila verticalmente
                    .padding(top = 25.dp), // Ajusta el padding superior para alinear los iconos
                horizontalArrangement = Arrangement.SpaceAround // Distribuye los ítems uniformemente
            ) {
                // Ítem de navegación para Main
                NavigationBarItem(
                    // selected = currentRoute == Main.toRoute(), // Ejemplo si usas rutas seguras
                    selected = navController.currentDestination?.route == Entry.toString(), // O compara con la ruta string si usas strings
                    onClick = {
                        // Navega a la pantalla principal.
                        // Usa popUpTo para evitar acumular instancias de la pantalla de inicio en el back stack
                        navController.navigate(Main) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true // Guarda el estado de la pantalla de inicio si vuelves a ella
                            }
                            // Evita crear múltiples copias de la misma pantalla si ya estás en ella
                            launchSingleTop = true
                            // Restaura el estado si ya estaba en el back stack
                            restoreState = true
                        }
                    },
                    icon = {
                        // Contenido del ícono (tu imagen)
                        Image(
                            painter = painterResource(id = R.drawable.cacaclick), // Asegúrate de que el recurso existe
                            contentScale = ContentScale.FillHeight, // Ajusta según necesites
                            modifier = Modifier.fillMaxHeight(0.8f), // Ajusta el tamaño del ícono
                            contentDescription = "Main Screen"
                        )
                    },
                    label = { /* Puedes añadir texto aquí si quieres */ },
                    alwaysShowLabel = false // Oculta el label si no está seleccionado
                )

                // Ítem de navegación para Upgrades
                NavigationBarItem(
                    // selected = currentRoute == Upgrades.toRoute(), // Ejemplo si usas rutas seguras
                    selected = navController.currentDestination?.route == Upgrades.toString(), // O compara con la ruta string si usas strings
                    onClick = {
                        navController.navigate(Upgrades) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        // Contenido del ícono (tu imagen)
                        Image(
                            painter = painterResource(id = R.drawable.flechaicono), // Asegúrate de que el recurso existe
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier.fillMaxHeight(0.8f),
                            contentDescription = "Upgrades Screen"
                        )
                    },
                    label = { /* Puedes añadir texto aquí si quieres */ },
                    alwaysShowLabel = false
                )

                // Otros ítems de navegación (ajusta según tus rutas y iconos)
                NavigationBarItem(
                    selected = false, // Ajusta la selección según la ruta actual
                    onClick = { /* TODO: Navegar a la pantalla 3 */ },
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.rocaicon), // Asegúrate de que el recurso existe
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier.fillMaxHeight(0.8f),
                            contentDescription = "Screen 3"
                        )
                    },
                    label = { /* Puedes añadir texto aquí si quieres */ },
                    alwaysShowLabel = false
                )

                NavigationBarItem(
                    selected = false, // Ajusta la selección según la ruta actual
                    onClick = { /* TODO: Navegar a la pantalla 4 */ },
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.dungeonicon), // Asegúrate de que el recurso existe
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier.fillMaxHeight(0.8f),
                            contentDescription = "Screen 4"
                        )
                    },
                    label = { /* Puedes añadir texto aquí si quieres */ },
                    alwaysShowLabel = false
                )
            }
        }
    }
}


