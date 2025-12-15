package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DesafiosSemanalesScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEDF5))
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color(0xFF7A2C54),
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onBack() }
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = "Desafíos Semanales",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7A2C54)
            )
        }

        Spacer(Modifier.height(18.dp))

        Text(
            text = "Los desafíos semanales son pequeñas metas para ayudarte a incorporar hábitos de bienestar en tu rutina.",
            fontSize = 15.sp,
            color = Color(0xFF7A2C54)
        )

        Spacer(Modifier.height(20.dp))

        // Tarjetas de desafíos
        DesafioCard(
            titulo = "Semana de la Atención Plena",
            descripcion = "Practica momentos de mindfulness cada día: observa tu respiración, tus sentidos o lo que estás haciendo."
        )

        Spacer(Modifier.height(12.dp))

        DesafioCard(
            titulo = "Semana del Auto-Cuidado",
            descripcion = "Incluye una acción de cuidado diario: hidratarte, dormir mejor, estirarte, tomar tu medicación, etc."
        )

        Spacer(Modifier.height(12.dp))

        DesafioCard(
            titulo = "Semana del Bienestar Físico",
            descripcion = "Muévete un poco cada día: caminar, bailar, hacer ejercicios suaves o salir a dar un paseo."
        )

        Spacer(Modifier.height(12.dp))

        DesafioCard(
            titulo = "Semana del Manejo del Estrés",
            descripcion = "Prueba estrategias para bajar el estrés: respiraciones profundas, pausas activas o escribir lo que sientes."
        )
    }
}

@Composable
fun DesafioCard(
    titulo: String,
    descripcion: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFCE7EF), RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Circulito a modo de icono
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(Color(0xFFFFA5C8), CircleShape)
        )

        Spacer(Modifier.width(12.dp))

        Column {
            Text(
                text = titulo,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF7A2C54)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = descripcion,
                fontSize = 14.sp,
                color = Color(0xFF7A2C54)
            )
        }
    }
}
