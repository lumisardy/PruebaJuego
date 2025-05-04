package com.example.pruebajuego.Screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.pruebajuego.R


@Composable
fun UpgradesScreen() {




    Box(Modifier.fillMaxSize()){

        Image(

            painter = painterResource(id = R.drawable.texturamadera), // Reemplaza con tu imagen
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop)


    }


}