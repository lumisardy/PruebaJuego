package com.example.pruebajuego.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pruebajuego.MainScreen

@Composable
fun NavigationWrapper(){

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Main){
        composable<Main>{
            MainScreen()
        }



    }

}