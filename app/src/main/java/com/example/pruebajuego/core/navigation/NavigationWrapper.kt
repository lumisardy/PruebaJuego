package com.example.pruebajuego.core.navigation

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pruebajuego.AudioViewModel
import com.example.pruebajuego.ComposablesVariados.GameSettingsScreen
import com.example.pruebajuego.EntryScreen
import com.example.pruebajuego.MainScreen
import com.example.pruebajuego.R

@Composable
fun NavigationWrapper(){


    val context = LocalContext.current
    val audioViewModel: AudioViewModel = viewModel()

    LaunchedEffect(Unit) {
        audioViewModel.startBackgroundMusic(context)
    }



    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Entry){
        composable<Main>{
            MainScreen({navController.navigate(Ajustes)})
        }

        composable<Entry>{
            EntryScreen({navController.navigate(Main)})
        }
        composable<Ajustes> {

            GameSettingsScreen()

        }



    }

}