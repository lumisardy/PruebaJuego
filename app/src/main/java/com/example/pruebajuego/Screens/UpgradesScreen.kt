package com.example.pruebajuego.Screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.packFloats
import com.example.pruebajuego.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpgradesScreen() {

    val pagerState = rememberPagerState(pageCount = { 2 })



    Scaffold(
    ) { innerPadding -> // 2. Scaffold te da los paddings internos necesarios.

        // 3. Aplicamos esos paddings a nuestro HorizontalPager.
        HorizontalPager(
            state = pagerState,
            // El modifier ahora ocupa todo el espacio y aplica el padding.
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // <-- ¡Esta es la línea clave!
        ) { page ->
            when(page)
            {
                0 -> PageUpgradesOne()
                1 -> PageUpgradeTwo()
            }
        }
    }


}