package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.viewmodel.MascotaViewModel

@Composable
fun NombrarMascotaScreen(
    mascota: String,
    onGuardarNombre: (String) -> Unit
) {
    val viewModel: MascotaViewModel = viewModel()
    val context = LocalContext.current
    var nombre by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Has elegido un $mascota",
            style = MaterialTheme.typography.headlineSmall
        )

        when (mascota) {
            "Hamster" -> Image(painterResource(R.drawable.hamster), contentDescription = null)
            "Mapache" -> Image(painterResource(R.drawable.mapache), contentDescription = null)
            "Zorro" -> Image(painterResource(R.drawable.zorro), contentDescription = null)
            "Perro" -> Image(painterResource(R.drawable.perro), contentDescription = null)
            "Nutria" -> Image(painterResource(R.drawable.nutria), contentDescription = null)
            "Oveja" -> Image(painterResource(R.drawable.oveja), contentDescription = null)
            "Gato" -> Image(painterResource(R.drawable.gato), contentDescription = null)


        }

        Spacer(Modifier.height(20.dp))
        Text(text = "¿Cómo quieres llamarlo?")
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            placeholder = { Text("Escribe un nombre") }
        )

        Spacer(Modifier.height(20.dp))
        Button(onClick = {
            viewModel.guardarNombre(context, nombre)
            onGuardarNombre(nombre)
        }) {
            Text("Guardar")
        }
    }
}
