package com.example.pruebajuego.OptimizacionesComposables

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PoopViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PoopViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PoopViewModel(context.applicationContext) as T // Usar applicationContext para evitar leaks
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// En tu actividad o lugar donde inicializas Compose:
// ... inside your ComponentActivity or Fragment ...
//setContent {
//    // ...
//    MainScreen(poopViewModelFactory = PoopViewModelFactory(this))
//    // ...
//}

// Tu MainScreen signature cambiaría para aceptar la factory
// @Composable
// fun MainScreen(poopViewModelFactory: PoopViewModelFactory) {
//     val poopViewModel: PoopViewModel = viewModel(factory = poopViewModelFactory)
//     val poopData = poopViewModel.poopData.collectAsState()
//     // ... resto del código ...
// }

// O, si solo la usas en MainScreen y no la pasas como parámetro,
// puedes obtenerla usando LocalContext dentro del Composable
/*
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val poopViewModel: PoopViewModel = viewModel(factory = PoopViewModelFactory(context.applicationContext))
    val poopData = poopViewModel.poopData.collectAsState()
    // ... resto del código ...
}
*/