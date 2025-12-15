package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.R
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavHostController
import com.example.mente_libre_app.navigation.Route
import com.example.mente_libre_app.ui.components.BottomNavigationBar
import com.example.mente_libre_app.ui.components.PetDrawable
import com.example.mente_libre_app.ui.viewmodel.PetViewModel

@Composable
fun CompaneraScreen(
    navController: NavHostController,
    petViewModel: PetViewModel
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItem = "companera",
                onItemSelected = { item ->
                    when (item) {
                        "inicio"    -> navController.navigate(Route.Inicio.path)
                        "companera" -> navController.navigate(Route.Companera.path)
                        "diario"    -> navController.navigate(Route.Diario.path)
                        "ajustes"   -> navController.navigate(Route.Ajustes.path)
                    }
                }
            )
        }
    ) { padding ->
        CompaneraContent(
            modifier = Modifier.padding(padding),
            navController = navController,
            petViewModel = petViewModel
        )
    }

}

@Composable
fun CompaneraContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    petViewModel: PetViewModel
) {
    val pet = petViewModel.pet.collectAsState().value

    LaunchedEffect(Unit) {
        if (pet == null) petViewModel.loadMyPet()
    }

    if (pet == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val avatarRes = PetDrawable(pet.avatarKey)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFFF8AAE), Color(0xFFFFC39F))
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {

            Spacer(Modifier.height(18.dp))

            OriginalCalendar()

            Spacer(Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CircleIcon(R.drawable.icon_ropa)
                CircleIcon(R.drawable.icon_help)
                CircleIcon(R.drawable.icon_hat)
            }

            Spacer(Modifier.height(10.dp))

            BurbujasDecorativas()

            /** 🔹 Mascota MUCHO MÁS ARRIBA */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-70).dp),     // <= sube bastante
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(avatarRes),
                    contentDescription = null,
                    modifier = Modifier.size(290.dp)
                )
            }

            Spacer(Modifier.height(5.dp))     // <= casi nada de espacio

            /** 🔹 Botón más arriba */
            BotonHablar(
                onClick = { navController.navigate(Route.Chat.path) }
            )


            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
fun OriginalCalendar() {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(Color.White.copy(alpha = 0.85f)),
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                listOf("20", "21", "22", "23", "24", "25", "26").forEach {
                    Text(it, color = Color(0xFF7A2C54), fontSize = 16.sp)
                }
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                listOf("Lu", "Ma", "Mi", "Ju", "Vi", "Sa", "Do").forEach {
                    Text(it, color = Color(0xFF7A2C54), fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun CircleIcon(icon: Int) {
    Box(
        modifier = Modifier
            .size(55.dp)
            .background(Color.White, RoundedCornerShape(50.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
fun BurbujasDecorativas() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        drawCircle(Color.White.copy(0.6f), radius = 8f, center = Offset(80f, 20f))
        drawCircle(Color.White.copy(0.5f), radius = 6f, center = Offset(200f, 60f))
        drawCircle(Color.White.copy(0.4f), radius = 5f, center = Offset(size.width - 70f, 40f))
        drawCircle(Color.White.copy(0.3f), radius = 4f, center = Offset(size.width / 2, 30f))
    }
}


@Composable
fun BotonHablar(onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        // Líneas laterales
        Divider(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 10.dp)
                .width(80.dp),
            color = Color(0xFF7A2C54).copy(alpha = 0.4f),
            thickness = 2.dp
        )

        Divider(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)
                .width(80.dp),
            color = Color(0xFF7A2C54).copy(alpha = 0.4f),
            thickness = 2.dp
        )

        // Botón
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(Color(0xFFFFE1C4)),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(0.85f)
        ) {
            Text(
                text = "¿Necesitas hablar?",
                color = Color(0xFF7A2C54),
                fontSize = 20.sp
            )
        }
    }
}

